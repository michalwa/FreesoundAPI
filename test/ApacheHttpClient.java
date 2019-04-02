import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import pl.michalwa.jfreesound.auth.UnauthorizedException;
import pl.michalwa.jfreesound.http.*;

/**
 * Implements the {@link HttpClient} interface with Apache's HTTP client library.
 */
public class ApacheHttpClient implements HttpClient
{
	private final org.apache.http.client.HttpClient http = HttpClientBuilder.create()
		.setConnectionManager(new PoolingHttpClientConnectionManager()).build();
	
	/**
	 * Builds a {@link HttpUriRequest} instance - a class from the Apache HTTP library
	 * based on the given JFreesound {@link HttpRequest} instance.
	 *
	 * @param request the request to construct
	 */
	private HttpUriRequest apacheHttpRequest(HttpRequest request) throws Exception
	{
		HttpUriRequest req;
		
		if(request.method() == HttpMethod.GET || request instanceof HttpGetRequest) {
			// Set URL
			req = new HttpGet(request.url());
		} else if(request instanceof HttpPostRequest) {
			
			// All POST requests coming from the library are form-urlencoded, so we don't need
			// to worry about other content types
			HttpEntity entity = EntityBuilder.create()
				.setText(((HttpPostRequest) request).body())
				.setContentType(ContentType.APPLICATION_FORM_URLENCODED)
				.setContentEncoding("UTF-8")
				.build();
			
			req = new HttpPost(request.url());
			((HttpPost) req).setEntity(entity);
			
		} else {
			throw new HttpException("Unsupported request type/method.");
		}
		
		// Set headers
		request.headers().forEach(req::addHeader);
		
		return req;
	}
	
	@Override
	public HttpResponse execute(HttpRequest request) throws HttpException, UnauthorizedException
	{
		// Construct request
		HttpUriRequest req;
		try {
			req = apacheHttpRequest(request);
		} catch(Exception e) {
			throw new HttpException(e);
		}
		
		// Send, get response
		org.apache.http.HttpResponse response;
		try {
			response = http.execute(req);
		} catch(IOException e) {
			throw new HttpException(e);
		}
		int status = response.getStatusLine().getStatusCode();
		
		// Check status
		if(status == 401) {
			throw new UnauthorizedException();
		} else if(status < 200 || status >= 300) {
			throw new HttpException("API responded with " + status + " (non 2xx status code).");
		}
		
		// Evaluate
		String body;
		try {
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch(IOException e) {
			throw new HttpException(e);
		}
		
		return () -> body;
	}
}
