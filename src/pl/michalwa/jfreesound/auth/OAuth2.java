package pl.michalwa.jfreesound.auth;

import com.google.gson.JsonObject;
import pl.michalwa.jfreesound.Freesound;
import pl.michalwa.jfreesound.http.HttpPostRequest;
import pl.michalwa.jfreesound.http.HttpRequest;
import pl.michalwa.jfreesound.utils.Promise;

/**
 * OAuth2 authentication method.
 *
 * <p> {@link Authentication} class that supports
 *     <a href="https://freesound.org/docs/api/authentication.html#oauth2-authentication">OAuth2 Authentication</a>.
 */
public class OAuth2 implements Authentication
{
	/** The final access token provided by the API after submitting the authorization code */
	private final String accessToken;
	/** The 'expires_in' value provided by the API when requesting the access token. */
	private int expiresIn = -1;
	/** The refresh token provided by the API when requesting the access token. */
	private String refreshToken = null;
	
	/**
	 * Initializes the OAuth2 authentication method with the given API access token. Use this if you
	 * already have acquired the access token. Otherwise, use {@link OAuth2#request()}.
	 *
	 * @param accessToken the already obtained access token
	 */
	public OAuth2(String accessToken)
	{
		this.accessToken = accessToken;
	}
	
	@Override
	public void processRequest(HttpRequest request)
	{
		request.header("Authorization", "Bearer " + accessToken);
	}
	
	/**
	 * Returns the 'expires_in' value provided by the API when requesting the access token,
	 * if such request has been made in order to construct this OAuth2 instance.
	 */
	public int expiresIn()
	{
		return expiresIn;
	}
	
	/**
	 * Returns the refresh token provided by the API when requesting the access token,
	 * if such request has been made in order to construct this OAuth2 instance.
	 */
	public String refreshToken()
	{
		return refreshToken;
	}
	
	/**
	 * Returns the API access token
	 */
	public String accessToken()
	{
		return accessToken;
	}
	
	/**
	 * Returns a new OAuth2 request object. Used for requesting OAuth2 access token.
	 */
	public static Request request()
	{
		return new Request();
	}
	
	/**
	 * A request to the API for the OAuth2 access token. Such a request can be made either with
	 * a temporary authorization code or with a refresh token, to refresh OAuth2 access to the API.
	 *
	 * <p> See more at <a href="https://freesound.org/docs/api/authentication.html#oauth2-authentication">OAuth2 Authentication</a>.
	 */
	public static class Request
	{
		private String clientId = null;
		private String clientSecret = null;
		private String refreshToken = null;
		private String authorizationCode = null;
		
		private Request() {}
		
		/**
		 * Sets the client credentials required to submit an OAuth2 access token request.
		 *
		 * @param id the API client ID
		 * @param secret the API client secret (token)
		 */
		public Request withCredentials(String id, String secret)
		{
			clientId = id;
			clientSecret = secret;
			return this;
		}
		
		/**
		 * Call this if you wish to retrieve the access token using the temporary authorization code.
		 *
		 * @param authCode the authorization code to use
		 */
		public Request withAuthCode(String authCode)
		{
			refreshToken = null;
			authorizationCode = authCode;
			return this;
		}
		
		/**
		 * Call this if you wish to refresh the access token using the refresh token.
		 *
		 * @param refreshToken the refresh token to use
		 */
		public Request withRefreshToken(String refreshToken)
		{
			authorizationCode = null;
			this.refreshToken = refreshToken;
			return this;
		}
		
		/**
		 * Submits the request to the API and returns a promise of a new OAuth2 instance.
		 *
		 * @return a promise of the resulting OAuth2 instance
		 */
		public Promise<OAuth2> submit()
		{
			if(clientId == null || clientSecret == null)
				throw new IllegalStateException("Credentials must be set before the request is submitted.");
			
			// Build the request
			HttpPostRequest request = new HttpPostRequest()
					.param("client_id", clientId)
					.param("client_secret", clientSecret)
					.path(Freesound.API_BASE_URL + "oauth2/access_token/");
			
			if(refreshToken != null) {
				request
					.param("grant_type", "refresh_token")
					.param("refresh_token", refreshToken);
			} else if(authorizationCode != null) {
				request
					.param("grant_type", "authorization_code")
					.param("code", authorizationCode);
			} else {
				throw new IllegalStateException("A request type must be set before the request is submitted.");
			}
			
			return new Promise<>(() -> {
				// Execute the request & parse the response
				JsonObject response = Freesound.json().parse(Freesound.httpClient().execute(request).body()).getAsJsonObject();
				if(response.has("access_token")) {
					OAuth2 auth = new OAuth2(response.get("access_token").getAsString());
					auth.expiresIn = response.get("expires_in").getAsInt();
					auth.refreshToken = response.get("refresh_token").getAsString();
					return auth;
				}
				
				throw new OAuth2Exception("Authentication failed.");
			});
		}
	}
}
