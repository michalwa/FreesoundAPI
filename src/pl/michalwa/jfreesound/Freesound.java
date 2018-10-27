package pl.michalwa.jfreesound;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Optional;
import org.apache.http.client.methods.HttpUriRequest;
import pl.michalwa.jfreesound.auth.Authentication;
import pl.michalwa.jfreesound.auth.OAuth2;
import pl.michalwa.jfreesound.auth.TokenAuthentication;
import pl.michalwa.jfreesound.data.Pack;
import pl.michalwa.jfreesound.data.Sound;
import pl.michalwa.jfreesound.data.User;
import pl.michalwa.jfreesound.http.HttpClient;
import pl.michalwa.jfreesound.request.*;
import pl.michalwa.jfreesound.utils.Promise;

/** The main API client class. Used to make requests and retrieve data from the API.
 * To use this class, build an instance with {@link Freesound.Builder Freesound.builder()}. */
public class Freesound
{
	/** The base path that all API request URLs begin with.
	 * Used by the {@link pl.michalwa.jfreesound.request.APIRequest} class to construct request URLs. */
	public static final String API_BASE_URL = "https://freesound.org/apiv2/";
	
	/** The authentication method used in API requests */
	private final Authentication auth;
	/** The HTTP client used to make requests to the API */
	private HttpClient http = HttpClient.defaultInstance();
	/** The parser used to parse json responses from the API */
	private JsonParser json = new JsonParser();
	/** The gson instance used to process responses */
	private Gson gson = new Gson();
	
	/** Constructs the API client */
	private Freesound(Authentication auth)
	{
		this.auth = auth;
	}
	
	/** Uses the given {@link HttpClient} instance to make HTTP requests */
	public Freesound use(HttpClient http)
	{
		if(http != null) this.http = http;
		return this;
	}
	
	/** Uses the given {@link JsonParser} to parse responses */
	public Freesound use(JsonParser json)
	{
		if(json != null) this.json = json;
		return this;
	}
	
	/** Uses the given {@link Gson} instance to deserialize responses */
	public Freesound use(Gson gson)
	{
		if(gson != null) this.gson = gson;
		return this;
	}
	
	/** Submits a request to the API and returns the result as JSON.
	 * @return The promise of a response as JSON. */
	public Promise<JsonObject> rawRequest(APIRequest request)
	{
		return new Promise<>(() -> {
			HttpUriRequest httpRequest = request.httpRequest();
			auth.processRequest(httpRequest);
			String response = http.execute(httpRequest).body();
			return json.parse(response).getAsJsonObject();
		});
	}
	
	/** Submits a request to the API and returns the result as an
	 * instance of the <code>TResponse</code> generic type of the given request
	 * @param <R> type of the response returned by the given request */
	public <R> Promise<R> request(APIRequest<R> request)
	{
		return rawRequest(request).then(response -> request.processResponse(response, gson));
	}
	
	/** Returns true, if this API client has been successfully authenticated with OAuth2 */
	public boolean isOAuth2Authenticated()
	{
		return (auth instanceof OAuth2);
	}
	
	/** Shorthand method - executes a {@link SimpleRequest} and returns the resulting promise */
	public Promise<JsonObject> request(Object... path)
	{
		return request(new SimpleRequest(path));
	}
	
	/** Shorthand method - executes a {@link SoundRequest} and returns the resulting promise */
	public Promise<Sound> sound(int id)
	{
		return request(new SoundRequest(id));
	}
	
	/** Shorthand method - executes a {@link UserRequest} and returns the resulting promise */
	public Promise<User> user(String username)
	{
		return request(new UserRequest(username));
	}
	
	/** Shorthand method - executes a {@link PackRequest} and returns the resulting promise */
	public Promise<Pack> pack(int id)
	{
		return request(new PackRequest(id));
	}
	
	/** Returns the default Freesound instance builder */
	public static Builder builder()
	{
		return new Builder();
	}
	
	/** Freesound instance builder */
	public static final class Builder
	{
		private Authentication auth = null;
		
		private Builder() {}
		
		/** Uses {@link TokenAuthentication} with the given token. */
		public Builder withToken(String token)
		{
			this.auth = new TokenAuthentication(token);
			return this;
		}
		
		/** Uses the specified authentication method */
		public Builder withAuthentication(Authentication auth)
		{
			this.auth = auth;
			return this;
		}
		
		/** Builds the API client instance */
		public Freesound build()
		{
			if(auth == null) throw new IllegalStateException("Authentication method must be set.");
			return new Freesound(auth);
		}
	}
}
