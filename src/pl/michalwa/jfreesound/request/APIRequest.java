package pl.michalwa.jfreesound.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import pl.michalwa.jfreesound.Freesound;
import pl.michalwa.jfreesound.http.HttpGetRequest;
import pl.michalwa.jfreesound.http.HttpRequest;

/**
 * The base interface of all requests that can be made to the API. A request stores some data
 * and builds an HTTP request based on that data.
 *
 * @param <T> the API resource data typed returned by the request
 */
public abstract class APIRequest<T>
{
	/**
	 * Builds the HTTP request.
	 */
	public HttpRequest httpRequest()
	{
		List<String> path = new ArrayList<>();
		Map<String, String> params = new HashMap<>();
		prepare(path, params);
		
		HttpRequest req = new HttpGetRequest().path(Freesound.API_BASE_URL);
		path.forEach(req::path);
		params.forEach(req::urlParam);
		
		return req;
	}
	
	/**
	 * The url path and query parameters are passed to this method for population.
	 *
	 * <p> The implementation of this method must populate the arrays with the appropriate data.
	 *
	 * @param path the URL path of this request - for example: if the desired
	 *     request url is {@code (api-url)/foo/bar}, this method must
	 *     populate the {@code path} list with the values {@code "foo"} and {@code "bar"}
	 *
	 * @param params a map of query params in the request URL passed to the method for population
	 */
	protected abstract void prepare(List<String> path, Map<String, String> params);
	
	/**
	 * Returns the processed (deserialized) response of the type specified by the return type of this request.
	 *
	 * @param response the response to process
	 * @param json json parser to be used to parse json explicitly
	 * @param gson {@link Gson} to parse the response automatically into a data type class instance
	 */
	public abstract T processResponse(JsonObject response, JsonParser json, Gson gson);
}
