package pl.michalwa.jfreesound.auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import javax.swing.text.html.Option;
import org.apache.http.client.methods.HttpUriRequest;
import pl.michalwa.jfreesound.Freesound;
import pl.michalwa.jfreesound.http.Http;
import pl.michalwa.jfreesound.http.HttpPostBuilder;
import pl.michalwa.jfreesound.utils.Promise;

/** The OAuth2 authentication method
 * @see <a href="https://freesound.org/docs/api/authentication.html#oauth2-authentication">OAuth2 Authentication</a> */
public class OAuth2 implements Authentication
{
	/** The final access token provided by the
	 * API after submitting the authorization code */
	private String accessToken;
	/** The 'expires_in' value provided by the
	 * API when requesting the access token. */
	private int expiresIn = -1;
	/** The refresh token provided by the API
	 * when requesting the access token. */
	private String refreshToken = null;
	
	/** Initializes the OAuth2 authentication method
	 * with the given API access token. Use this if you
	 * already have acquired the access token. Otherwise,
	 * see {@link OAuth2#request()}. */
	public OAuth2(String accessToken)
	{
		this.accessToken = accessToken;
	}
	
	@Override
	public void processRequest(HttpUriRequest request)
	{
		request.setHeader("Authorization", "Bearer " + accessToken);
	}
	
	/** Returns the 'expires_in' value provided by the
	 * API when requesting the access token, if such request has
	 * been made to construct this OAuth2 instance.
	 * @returns the 'expires_in' value or <code>-1</code> if no request has been made */
	public int expiresIn()
	{
		return expiresIn;
	}
	
	/** Returns the refresh token provided by the
	 * API when requesting the access token, if such request has
	 * been made to construct this OAuth2 instance.
	 * @returns the refresh token or null if no request has been made */
	public String refreshToken()
	{
		return refreshToken;
	}
	
	/** Returns the API access token */
	public String accessToken()
	{
		return accessToken;
	}
	
	/** Returns a new OAuth2 request object. Used
	 * for requesting OAuth2 access token. */
	public static Request request()
	{
		return new Request();
	}
	
	/** A request for the OAuth2 access token.
	 * Such a request can be made either with
	 * a temporary authorization code or with a refresh
	 * token, to refresh OAuth2 access to the API. */
	public static class Request
	{
		/* Parameters */
		private String clientId = null;
		private String clientSecret = null;
		private String refreshToken = null;
		private String authorizationCode = null;
		
		/** An HTTP client instance used to make requests for
		 * the access token to the API. */
		private Http http = null;
		private JsonParser json = new JsonParser();
		
		private Request() {}
		
		/** Sets the client credentials. Required
		 * to submit an OAuth2 request.
		 * @param id the API client ID
		 * @param secret the API client secret (token) */
		public Request withCredentials(String id, String secret)
		{
			clientId = id;
			clientSecret = secret;
			return this;
		}
		
		/** Call this if you wish to retrieve the access token
		 * using the temporary authorization code. */
		public Request withAuthCode(String authCode)
		{
			refreshToken = null;
			authorizationCode = authCode;
			return this;
		}
		
		/** Call this if you wish to refresh the access token
		 * using the refresh token. */
		public Request withRefreshToken(String refreshToken)
		{
			authorizationCode = null;
			this.refreshToken = refreshToken;
			return this;
		}
		
		/** Sets this request to use a custom HTTP client
		 * instance instead of {@link Http#defaultClient()}. */
		public Request withHttpClient(Http http)
		{
			this.http = http;
			return this;
		}
		
		/** Submits the request and returns a promise of a new OAuth2 instance */
		public Promise<OAuth2> submit()
		{
			return new Promise<>(() -> {
				if(clientId == null || clientSecret == null)
					throw new IllegalStateException("Credentials must be set before the request is submitted.");
				
				http = Optional.ofNullable(http).orElse(Http.defaultClient());
				
				// Build the request
				HttpPostBuilder post = new HttpPostBuilder(Freesound.API_BASE_URL + "oauth2/access_token/")
						.param("client_id", clientId)
						.param("client_secret", clientSecret);
				
				if(refreshToken != null) {
					post.param("grant_type", "refresh_token")
							.param("refresh_token", refreshToken);
				} else if(authorizationCode != null) {
					post.param("grant_type", "authorization_code")
							.param("code", authorizationCode);
				} else {
					throw new IllegalStateException("A request type must be set before the request is submitted.");
				}
				
				// Execute the request & parse the response
				JsonObject response = json.parse(http.executeAndRead(post.build())).getAsJsonObject();
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
