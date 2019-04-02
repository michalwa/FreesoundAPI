package pl.michalwa.jfreesound;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Objects;
import pl.michalwa.jfreesound.auth.Authentication;
import pl.michalwa.jfreesound.auth.OAuth2;
import pl.michalwa.jfreesound.auth.TokenAuthentication;
import pl.michalwa.jfreesound.data.Pack;
import pl.michalwa.jfreesound.data.Sound;
import pl.michalwa.jfreesound.data.User;
import pl.michalwa.jfreesound.http.HttpClient;
import pl.michalwa.jfreesound.http.HttpRequest;
import pl.michalwa.jfreesound.request.*;
import pl.michalwa.jfreesound.utils.Promise;

/**
 * The main library class. Used to submit requests and process data from the API.
 */
public class Freesound
{
	/** The base path that all API request URLs begin with. Used by
	 * {@link pl.michalwa.jfreesound.request.APIRequest} to construct request URLs. */
	public static final String API_BASE_URL = "https://freesound.org/apiv2/";
	
	/** The {@link HttpClient} implementation to use across the library. */
	private static HttpClient httpClient = null;
	/** The parser used to parse json responses from the API */
	private static final JsonParser json = new JsonParser();
	/** The gson instance used to process responses */
	private static final Gson gson = new Gson();
	
	/** The authentication method used in API requests */
	private final Authentication auth;
	
	private Freesound(Authentication auth)
	{
		this.auth = auth;
	}
	
	/**
	 * Submits a request to the API and returns the result as JSON.
	 *
	 * @return The promise of a response as JSON.
	 */
	public Promise<JsonObject> rawRequest(APIRequest request)
	{
		return new Promise<>(() -> {
			HttpRequest httpRequest = request.httpRequest();
			auth.processRequest(httpRequest);
			String response = httpClient().execute(httpRequest).body();
			return json.parse(response).getAsJsonObject();
		});
	}
	
	/**
	 * Submits a request to the API and returns a promise of the result as an instance
	 * of the generic type of the request.
	 *
	 * @param <T> type of the response returned by the given request
	 */
	public <T> Promise<T> request(APIRequest<T> request)
	{
		return rawRequest(request).then(response -> request.processResponse(response, json, gson));
	}
	
	/**
	 * Returns {@code true}, if the authentication method is set to an {@link OAuth2} instance.
	 */
	public boolean isOAuth2Authenticated()
	{
		return (auth instanceof OAuth2);
	}
	
	/**
	 * Executes a {@link SimpleRequest} and returns the resulting promise
	 *
	 * @param path the path of the request
	 */
	public Promise<JsonObject> request(Object... path)
	{
		return request(new SimpleRequest(path));
	}
	
	/**
	 * Executes a {@link SoundRequest} and returns the resulting promise
	 *
	 * @param id the sound id
	 */
	public Promise<Sound> sound(int id)
	{
		return request(new SoundRequest(id));
	}
	
	/**
	 * Executes a {@link UserRequest} and returns the resulting promise
	 *
	 * @param username the user's name
	 */
	public Promise<User> user(String username)
	{
		return request(new UserRequest(username));
	}
	
	/**
	 * Executes a {@link PackRequest} and returns the resulting promise
	 *
	 * @param id the pack id
	 */
	public Promise<Pack> pack(int id)
	{
		return request(new PackRequest(id));
	}
	
	/**
	 * Sets the {@link HttpClient} implementation to use across the library.
	 *
	 * @param client the {@link HttpClient} to use
	 */
	public static void setHttpClient(HttpClient client)
	{
		httpClient = Objects.requireNonNull(client);
	}
	
	/**
	 * Returns the http client set to be used by the library.
	 */
	public static HttpClient httpClient()
	{
		if(httpClient == null)
			throw new IllegalStateException("HttpClient not set.");
		
		return httpClient;
	}
	
	/**
	 * Returns the json parser set to be used by the library.
	 */
	public static JsonParser json()
	{
		return json;
	}
	
	/**
	 * Returns the {@link Gson} instance set to be used by the library.
	 */
	public static Gson gson()
	{
		return gson;
	}
	
	/**
	 * Returns the default {@link Freesound} instance builder
	 */
	public static Builder builder()
	{
		return new Builder();
	}
	
	/**
	 * {@link Freesound} builder
	 */
	public static final class Builder
	{
		private Authentication auth = null;
		
		private Builder() {}
		
		/**
		 * Uses {@link TokenAuthentication} with the given token.
		 *
		 * @param token the token to use
		 */
		public Builder withToken(String token)
		{
			this.auth = new TokenAuthentication(token);
			return this;
		}
		
		/**
		 * Uses the specified authentication method
		 *
		 * @param auth the authentication method to use
		 */
		public Builder withAuthentication(Authentication auth)
		{
			this.auth = auth;
			return this;
		}
		
		/**
		 * Builds the API client instance
		 *
		 * @return the created {@link Freesound} instance
		 */
		public Freesound build()
		{
			if(auth == null)
				throw new IllegalStateException("Authentication method must be set.");
			
			return new Freesound(auth);
		}
	}
}
