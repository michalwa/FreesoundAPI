package pl.michalwa.jfreesound;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import org.apache.http.client.methods.HttpUriRequest;
import pl.michalwa.jfreesound.auth.Authentication;
import pl.michalwa.jfreesound.auth.TokenAuthentication;
import pl.michalwa.jfreesound.http.Http;
import pl.michalwa.jfreesound.request.APIRequest;

/** The main API client class. Used to make requests and retrieve
 * data from the API. To use this class, firstly build an instance
 * with {@link Freesound.Builder Freesound.builder()}. */
public class Freesound
{
	/** The base uri that all API request URLs begin with.
	 * Used by the {@link APIRequest} class to construct request URLs. */
	public static final String API_BASE_URL = "https://freesound.org/apiv2/";
	
	/** The authentication method used in API requests */
	private Authentication auth;
	/** The HTTP client used to make requests to the API */
	private Http http;
	/** The parser used to parse json responses from the API */
	private JsonParser json = new JsonParser();
	
	/** Constructs the API client */
	private Freesound(Authentication auth, Http http)
	{
		this.auth = auth;
		this.http = http;
	}
	
	/** Submits a request to the API and returns the result as JSON.
	 * @throws IOException if the HTTP GET request fails */
	public JsonObject request(APIRequest request) throws IOException
	{
		HttpUriRequest httpRequest = request.httpRequest();
		auth.processRequest(httpRequest);
		String response = http.executeAndRead(httpRequest);
		return json.parse(response).getAsJsonObject();
	}
	
	/** Submits a request to the API and returns the result as JSON.
	 * Unlike {@link Freesound#request(APIRequest)} it catches the exception
	 * and passes it to the given {@link Consumer}. If the consumer is null,
	 * the error is printed out to the standard error output stream.
	 * @returns The response as JSON or <code>null</code> if the request fails. */
	public JsonObject request(APIRequest request, Consumer<IOException> onError)
	{
		JsonObject result = null;
		try {
			result = request(request);
		} catch(IOException e) {
			Optional.ofNullable(onError).orElse(IOException::printStackTrace).accept(e);
		}
		return result;
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
		private Http http = null;
		
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
		public Builder withHttpClient(Http http)
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
			http = Optional.ofNullable(http).orElseGet(Http::defaultClient);
			
			return new Freesound(auth, http);
		}
	}
}
