package pl.michalwa.jfreesound.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/** A promise of an asyncronous task that it will
 * return a value of the generic type <code>T</code>.
 * @param <T> the type of the returned value */
public class Promise<T>
{
	private Thread thread;
	private Callable<T> callable;
	private List<Consumer<T>> onDone = new ArrayList<>();
	private List<Consumer<Exception>> onFail = new ArrayList<>();
	private volatile T value = null;
	private volatile Exception exception = null;
	
	/** Performs a call to the given callable on
	 * a separate thread and depending on the
	 * result of that call either calls the <code>onDone</code>
	 * listeners or <code>onFail</code> listeners, when the
	 * call results in an exception. */
	public Promise(Callable<T> callable)
	{
		this.callable = callable;
		thread = new Thread(() -> {
			try {
				value = callable.call();
				onDone.forEach(c -> c.accept(value));
			} catch(Exception e) {
				exception = e;
				onFail.forEach(c -> c.accept(exception));
			}
		});
		thread.start();
	}
	
	/** Adds a listener to the promise that will get executed
	 * when the promise is satisfied.
	 * @return this */
	public Promise<T> onDone(Consumer<T> action)
	{
		onDone.add(action);
		return this;
	}
	
	/** Adds an exception listener to the promise that will
	 * get called when the promising task results in an exception.
	 * @return this */
	public Promise<T> onFail(Consumer<Exception> action)
	{
		onFail.add(action);
		return this;
	}
	
	/** Blocks until the promising call finishes. After that
	 * it either returns the value of that call or throws an
	 * exception, if the call also resulted in an exception. */
	public T await() throws Exception
	{
		while(true) {
			if(exception != null) throw exception;
			else if(value != null) return value;
		}
	}
	
	/** Calls {@link Promise#await()} and catches any exception */
	public T awaitAndCatch()
	{
		try {
			return await();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** Same as {@link Promise#await()}, but with a timeout.
	 * If the timeout runs out and the promise hasn't been fulfilled,
	 * this method will throw a {@link TimeoutException}.
	 * @param timeout the timeout in milliseconds */
	public T await(long timeout) throws Exception
	{
		long beginTime = System.currentTimeMillis();
		while(true) {
			if(exception != null) throw exception;
			else if(value != null) return value;
			
			if(System.currentTimeMillis() - beginTime > timeout)
				throw new TimeoutException("Awaited promise has not been fulfilled.");
		}
	}
}
