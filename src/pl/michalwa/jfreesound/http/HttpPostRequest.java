package pl.michalwa.jfreesound.http;

import java.util.HashMap;
import java.util.Map;

/**
 * A POST-type HTTP request
 */
public class HttpPostRequest extends HttpRequestWithBody<Map<String, String>>
{
	private final Map<String, String> params = new HashMap<>();
	
	@Override
	public HttpMethod method()
	{
		return HttpMethod.POST;
	}
	
	/**
	 * Returns a copy of the POST parameters map for the request
	 */
	@Override
	public Map<String, String> body()
	{
		return new HashMap<>(params);
	}
	
	@Override
	public HttpPostRequest path(String path)
	{
		super.path(path);
		return this;
	}
	
	@Override
	public HttpPostRequest urlParam(String key, String value)
	{
		super.urlParam(key, value);
		return this;
	}
	
	/**
	 * Sets a POST parameter for the request
	 *
	 * @param key the parameter name
	 * @param value the value for the parameter
	 */
	public HttpPostRequest param(String key, String value)
	{
		params.put(key, value);
		return this;
	}
}
