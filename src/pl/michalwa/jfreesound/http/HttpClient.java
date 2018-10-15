package pl.michalwa.jfreesound.http;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import pl.michalwa.jfreesound.auth.UnauthorizedException;

/** A wrapper for the HTTP client, used to make HTTP requests */
public interface HttpClient
{
	/** Executes the given request and returns the response. */
	HttpResponse execute(HttpUriRequest request) throws IOException;
	
	/** Returns the default HTTP client implementation
	 * using {@link org.apache.http.client.HttpClient}. */
	static HttpClient defaultInstance()
	{
		return new HttpClient()
		{
			private org.apache.http.client.HttpClient http = HttpClientBuilder.create()
					.setConnectionManager(new PoolingHttpClientConnectionManager()).build();
			
			@Override
			public HttpResponse execute(HttpUriRequest request) throws IOException
			{
				org.apache.http.HttpResponse response = http.execute(request);
				int status = response.getStatusLine().getStatusCode();
				if(status == 401)
					throw new UnauthorizedException("API responded with 401 (Unauthorized).");
				else if(status < 200 || status >= 300)
					throw new HttpResponseException(status, "API responded with " + status + " (non 2xx status code).");
				
				return () -> {
					HttpEntity entity = response.getEntity();
					String body = EntityUtils.toString(entity);
					EntityUtils.consume(entity);
					return body;
				};
			}
		};
	}
}
