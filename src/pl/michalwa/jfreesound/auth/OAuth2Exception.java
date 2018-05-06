package pl.michalwa.jfreesound.auth;

/** Thrown when OAuth2 authentication fails */
public class OAuth2Exception extends RuntimeException
{
	public OAuth2Exception(String message)
	{
		super(message);
	}
}
