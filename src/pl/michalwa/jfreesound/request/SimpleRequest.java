package pl.michalwa.jfreesound.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/** A basic request implementation. Should only
 * be used for testing. */
public class SimpleRequest extends APIRequest<JsonObject>
{
	/** Parts of the request URL */
	private final Object[] parts;
	
	/** Constructs a simple request with the given sub-path */
	public SimpleRequest(String path)
	{
		parts = new String[] { path };
	}
	
	/** Constructs a simple request from the given sub-path parts */
	public SimpleRequest(Object... path)
	{
		parts = path;
	}
	
	@Override
	protected void prepare(List<String> path, Map<String, String> params)
	{
		Stream.of(parts).map(Object::toString).forEach(path::add);
	}
	
	@Override
	public JsonObject processResponse(JsonObject response, Gson gson)
	{
		return response;
	}
}
