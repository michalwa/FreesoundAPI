package pl.michalwa.jfreesound.auth;

import java.io.IOException;

/**
 * Thrown when a request submitted to the API results in a 401 status code (Unauthorized)
 */
public class UnauthorizedException extends IOException
{
	public UnauthorizedException()
	{
		super("API responded with 401 (Unauthorized).");
	}
	
	public UnauthorizedException(String message)
	{
		super(message);
	}
	
	public UnauthorizedException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public UnauthorizedException(Throwable cause)
	{
		super(cause);
	}
}
