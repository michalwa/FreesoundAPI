package pl.michalwa.jfreesound.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
		try {
			URIBuilder uri = new URIBuilder(Freesound.API_BASE_URL);
			
			// Process the path
			List<String> path = new ArrayList<>();
			Map<String, String> params = new HashMap<>();
			prepare(path, params);
			uri.setPath(uri.getPath() + String.join("/", path));
			uri.setParameters(params.entrySet().stream()
			                        .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
			                        .collect(Collectors.toList()));
			
			return new HttpGet(uri.toString());
		} catch(URISyntaxException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/** The url path and query parameters are passed to this method for population.
	 * The implementation of this method must populate the arrays with the appropriate data.
	 * @param path the URL path of this request - for example: if the desired
	 *             request url is {@code (api-url)/foo/bar}, this method must
	 *             populate the {@code path} list with the values {@code "foo"} and {@code "bar"}
	 * @param params a map of query params in the request URL passed to the method
	 *               for population */
	protected abstract void prepare(List<String> path, Map<String, String> params);
	
	/** Returns the processed (deserialized) response of the type specified by the return type of this request.*/
	public abstract TResponse processResponse(JsonObject response, Gson gson);
}
