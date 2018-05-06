package pl.michalwa.jfreesound.http;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import pl.michalwa.jfreesound.auth.UnauthorizedException;

/** A facade interface for the HTTP client,
 * used to make HTTP requests */
public abstract class Http
{
	/** Executes the given request and returns the response. */
	public abstract HttpResponse execute(HttpUriRequest request) throws IOException;
	
	/** Executes the given request and returns the body
	 * of the response.
	 * @throws UnauthorizedException if the request results in a 401 status response
	 */
	public String executeAndRead(HttpUriRequest request) throws IOException
	{
		HttpResponse response = execute(request);
		int status = response.getStatusLine().getStatusCode();
		if(status == 401)
			throw new UnauthorizedException("API responded with 401 (Unauthorized).");
		else if(Math.floorDiv(status, 100) != 2)
			throw new HttpResponseException(status, "API responded with " + status + " (non 2xx status code).");
		
		HttpEntity entity = response.getEntity();
		String body = EntityUtils.toString(entity);
		
		EntityUtils.consume(entity);
		return body;
	}
	
	/** Returns the default HTTP client implementation
	 * using {@link HttpClient org.apache.http.client.HttpClient}. */
	public static Http defaultClient()
	{
		return new Http()
		{
			private HttpClient http = HttpClientBuilder.create().build();
			
			@Override
			public HttpResponse execute(HttpUriRequest request) throws IOException
			{
				return http.execute(request);
			}
		};
	}
}
