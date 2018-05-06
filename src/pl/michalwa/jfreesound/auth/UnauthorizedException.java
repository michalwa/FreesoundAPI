package pl.michalwa.jfreesound.auth;

/** Thrown when a request submitted to the API results
 * in a 401 status code (Unauthorized) */
public class UnauthorizedException extends RuntimeException
{
	public UnauthorizedException(String message)
	{
		super(message);
	}
}
