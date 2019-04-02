package pl.michalwa.jfreesound.http;

/**
 * Occurs when an {@link HttpClient} fails to return a response.
 */
public class HttpException extends Exception
{
	public HttpException() {}
	
	public HttpException(String message)
	{
		super(message);
	}
	
	public HttpException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public HttpException(Throwable cause)
	{
		super(cause);
	}
}
