package pl.michalwa.jfreesound;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Optional;
import org.apache.http.client.methods.HttpUriRequest;
import pl.michalwa.jfreesound.auth.Authentication;
import pl.michalwa.jfreesound.auth.OAuth2;
import pl.michalwa.jfreesound.auth.TokenAuthentication;
import pl.michalwa.jfreesound.http.HttpClient;
import pl.michalwa.jfreesound.request.APIRequest;
import pl.michalwa.jfreesound.utils.Promise;

/** The main API client class. Used to make requests and retrieve
 * data from the API. To use this class, firstly build an instance
 * with {@link Freesound.Builder Freesound.builder()}. */
public class Freesound
{
	/** The base path that all API request URLs begin with.
	 * Used by the {@link pl.michalwa.jfreesound.request.APIRequest} class to construct request URLs. */
	public static final String API_BASE_URL = "https://freesound.org/apiv2/";
	
	/** The authentication method used in API requests */
	private Authentication auth;
	/** The HTTP client used to make requests to the API */
	private HttpClient http;
	/** The parser used to parse json responses from the API */
	private JsonParser json = new JsonParser();
	
	/** Constructs the API client */
	private Freesound(Authentication auth, HttpClient http)
	{
		this.auth = auth;
		this.http = http;
	}
	
	/** Submits a request to the API and returns the result as JSON.
	 * @return The promise of a response as JSON. */
	public Promise<JsonObject> rawRequest(APIRequest request)
	{
		return new Promise<>(() -> {
			HttpUriRequest httpRequest = request.httpRequest();
			auth.processRequest(httpRequest);
			String response = http.executeAndRead(httpRequest);
			return json.parse(response).getAsJsonObject();
		});
	}
	
	/** Submits a request to the API and returns the result as an
	 * instance of the <code>TResponse</code> generic type of the given request
	 * @param <R> type of the response returned by the given request */
	public <R> Promise<R> request(APIRequest<R> request)
	{
		return rawRequest(request).then(request::processResponse);
	}
	
	/** Returns true, if this API client has
	 * been successfully authenticated with OAuth2. */
	public boolean isOAuth2Authenticated()
	{
		return (auth instanceof OAuth2);
	}
	
	/** Returns the default Freesound instance builder */
	public static Builder builder()
	{
		return new Builder();
	}
	
	/** Freesound instance builder */
	public static final class Builder
	{
		/* Parameters */
		private Authentication auth = null;
		private HttpClient http = null;
		
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
		
		/** Uses the given HTTP client to make http requests
		 * to the API instead of the default one. */
		public Builder withHttpClient(HttpClient http)
		{
			this.http = http;
			return this;
		}
		
		/** Builds the API client instance */
		public Freesound build()
		{
			// Required parameters
			if(auth == null) throw new IllegalStateException("Authentication method must be set.");
			
			// Optional parameters
			http = Optional.ofNullable(http).orElseGet(HttpClient::defaultInstance);
			
			return new Freesound(auth, http);
		}
	}
}
