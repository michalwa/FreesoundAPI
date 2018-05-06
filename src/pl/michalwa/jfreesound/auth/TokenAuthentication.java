package pl.michalwa.jfreesound.auth;

import org.apache.http.client.methods.HttpUriRequest;

/** The basic authentication method using an access token */
public class TokenAuthentication implements Authentication
{
	/** The API token used for authentication */
	private String token;
	
	/** Constructs an instance of the basic authentication method
	 * using the given API access token (the client secret). */
	public TokenAuthentication(String token)
	{
		this.token = token;
	}
	
	@Override
	public void processRequest(HttpUriRequest request)
	{
		request.setHeader("Authorization", "Token " + token);
	}
}
