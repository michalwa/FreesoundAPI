package pl.michalwa.jfreesound.request;

import com.google.gson.JsonObject;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import pl.michalwa.jfreesound.Freesound;

/** The base interface of all requests that can
 * be made to the API. A request stores some data
 * and builds an HTTP request based on that data. */
public abstract class APIRequest<TResponse>
{
	/** Builds the HTTP request. */
	public HttpUriRequest httpRequest()
	{
		String uri = Freesound.API_BASE_URL + uri();
		Map<String, String> params = urlParams();
		if(!params.isEmpty()) {
			try {
				URIBuilder builder = new URIBuilder(uri);
				builder.addParameters(urlParams().entrySet().stream()
						.map(e -> new BasicNameValuePair(e.getKey(), e.getValue()))
						.collect(Collectors.toList()));
				uri = builder.build().toString();
			} catch(URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return new HttpGet(uri);
	}
	
	/** Returns the request sub-url (the part
	 * of the url after the base url) for this request.
	 * For example, if the request url is
	 * <code>https://freesound.org/apiv2/foo/bar/</code>,
	 * then this method must return <code>foo/bar/</code>. */
	protected abstract String uri();
	
	/** Returns the processed response of the
	 * type specified by the return type of this request.*/
	public abstract TResponse processResponse(JsonObject response);
	
	/** Returns the URL (GET) parameters for this request */
	protected Map<String, String> urlParams()
	{
		return Collections.emptyMap();
	}
	
	/** Joins the given uri parts into a request sub-uri
	 * using the '/' (slash) separator. */
	public static String joinURL(Object... parts)
	{
		StringJoiner joiner = new StringJoiner("/");
		Stream.of(parts).forEach(obj -> joiner.add(obj.toString()));
		return joiner.toString();
	}
}
