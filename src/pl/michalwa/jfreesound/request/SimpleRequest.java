package pl.michalwa.jfreesound.request;

import com.google.gson.JsonObject;

/** A basic request implementation. Should only
 * be used for testing. */
public class SimpleRequest extends APIRequest<JsonObject>
{
	/** Parts of the request URL */
	private Object[] parts;
	
	/** Constructs a simple request with the given sub-uri */
	public SimpleRequest(String url)
	{
		parts = new String[] { url };
	}
	
	/** Constructs a simple request from the given sub-uri parts */
	public SimpleRequest(Object... url)
	{
		parts = url;
	}
	
	@Override
	protected String uri()
	{
		return joinURL(parts);
	}
	
	@Override
	public JsonObject processResponse(JsonObject response)
	{
		return response;
	}
}
