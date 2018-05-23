package pl.michalwa.jfreesound.http;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import pl.michalwa.jfreesound.auth.UnauthorizedException;

/** A wrapper for the HTTP client, used to make HTTP requests */
public abstract class HttpClient
{
	/** Executes the given request and returns the response. */
	public abstract HttpResponse execute(HttpUriRequest request) throws IOException;
	
	/** Executes the given request and returns the body
	 * of the response.
	 * @throws pl.michalwa.jfreesound.auth.UnauthorizedException if the request results in a 401 status response
	 * @throws org.apache.http.client.HttpResponseException if the request results in a non 2xx status response */
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
	 * using {@link org.apache.http.client.HttpClient}. */
	public static HttpClient defaultInstance()
	{
		return new HttpClient()
		{
			private org.apache.http.client.HttpClient http = HttpClientBuilder.create().build();
			
			@Override
			public HttpResponse execute(HttpUriRequest request) throws IOException
			{
				return http.execute(request);
			}
		};
	}
}
