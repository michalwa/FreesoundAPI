package pl.michalwa.jfreesound.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A promise of an asyncronous task that will return a value of the generic type {@code T}.
 *
 * <p> This utility is to resemble the promise functionality used in JavaScript
 *
 * @param <T> the type of the returned value
 */
public class Promise<T>
{
	/** Executor service used to instantiate threads */
	private static final ExecutorService executor = Executors.newCachedThreadPool();
	
	/** The future task that executes the promise callable */
	private final Future<?> task;
	/** A list of listeners for when the promise is fulfilled */
	private final List<Consumer<T>> onDone = new ArrayList<>();
	/** A list of listeners for when the promise fails */
	private final List<Consumer<Exception>> onFail = new ArrayList<>();
	/** Stores the evaluated value of the promise */
	private volatile T value = null;
	/** Stores any exception that will occur during the execution of the promise callable */
	private volatile Exception exception = null;
	
	/**
	 * Performs a call to the given callable on a separate thread and depending on the result
	 * of that call either calls the {@code onDone} listeners or {@code onFail} listeners,
	 * when the call results in an exception.
	 */
	public Promise(Callable<T> callable)
	{
		task = executor.submit(() -> {
			try {
				value = callable.call();
				onDone.forEach(c -> c.accept(value));
			} catch(Exception e) {
				exception = e;
				onFail.forEach(c -> c.accept(exception));
			}
		});
	}
	
	/**
	 * Returns a new promise that will asynchronously await for this promise to fulfill,
	 * then pass the result through the given function and return it as its result.
	 */
	public <Next> Promise<Next> then(Function<T, Next> action)
	{
		return new Promise<>(() -> action.apply(await()));
	}
	
	/**
	 * Returns a new promise that will asynchronously await the promise
	 * produced by the given function after calling with this promise's result.
	 */
	public <Next> Promise<Next> thenPromise(Function<T, Promise<Next>> action)
	{
		return new Promise<>(() -> action.apply(await()).await());
	}
	
	/**
	 * Adds a listener to this promise that will get executed when the promise is fulfilled.
	 */
	public Promise<T> onDone(Consumer<T> action)
	{
		onDone.add(action);
		return this;
	}
	
	/**
	 * Adds an exception listener to this promise that will get called when the promising task results in an exception.
	 */
	public Promise<T> onFail(Consumer<Exception> action)
	{
		onFail.add(action);
		return this;
	}
	
	/**
	 * Blocks until the promising call finishes. After that it either returns the value of that call or throws an
	 * exception, if the call resulted in one.
	 *
	 * @return the result of the promise
	 */
	public T await() throws Exception
	{
		// Await 'done'
		while(!task.isDone()) {}
		
		// Return the output
		if(task.isCancelled()) {
			throw new PromiseAwaitException("Awaited promise has been cancelled.");
		} else if(exception != null) {
			throw exception;
		}
		return value;
	}
	
	/**
	 * Same as {@link Promise#await()}, but with a timeout. If the timeout runs out and the promise hasn't
	 * been fulfilled, this method will throw a {@link TimeoutException}.
	 *
	 * @param timeout the timeout in milliseconds
	 *
	 * @return the result of the promise
	 */
	public T await(long timeout) throws Exception
	{
		// Await 'done'
		long beginTime = System.currentTimeMillis();
		while(!task.isDone()) {
			if(System.currentTimeMillis() - beginTime > timeout)
				throw new TimeoutException("Awaited promise has not been fulfilled.");
		}
		
		// Return the output
		if(task.isCancelled()) {
			throw new PromiseAwaitException("Awaited promise has been cancelled.");
		} else if(exception != null) {
			throw exception;
		}
		return value;
	}
	
	/**
	 * Calls {@link Promise#await()}, but catches and prints any exceptions
	 *
	 * @return the result of the promise
	 */
	public T safeAwait()
	{
		try {
			return await();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Calls {@link Promise#await(long)}, but catches and prints any exceptions
	 *
	 * @return the result of the promise
	 */
	public T safeAwait(long timeout)
	{
		try {
			return await(timeout);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Cancels this promise. If the promise task is running, it will be interrupted.
	 * This method has no effect, if the promise has already been canceled.
	 */
	public void cancel()
	{
		task.cancel(true);
	}
	
	/**
	 * Thrown when an awaited promise is cancelled
	 */
	public static class PromiseAwaitException extends Exception
	{
		private PromiseAwaitException(String s)
		{
			super(s);
		}
	}
}
