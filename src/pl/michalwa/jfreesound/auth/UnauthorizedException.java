package pl.michalwa.jfreesound.auth;

import java.io.IOException;

/** Thrown when a request submitted to the API results
 * in a 401 status code (Unauthorized) */
public class UnauthorizedException extends IOException
{
	public UnauthorizedException(String message)
	{
		super(message);
	}
}
