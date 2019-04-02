package pl.michalwa.jfreesound.auth;

import pl.michalwa.jfreesound.http.HttpRequest;

/**
 * The basic authentication method using an access token
 */
public class TokenAuthentication implements Authentication
{
	/**
	 * The API token used for authentication
	 */
	private final String token;
	
	/**
	 * Constructs an instance of the basic authentication method using the given API access token (the client secret).
	 *
	 * @param token the authentication token to use
	 */
	public TokenAuthentication(String token)
	{
		this.token = token;
	}
	
	@Override
	public void processRequest(HttpRequest request)
	{
		// Why Freesound API doesn't use 'Bearer' as the authentication type is weird but is not our problem
		request.header("Authorization", "Token " + token);
	}
}
