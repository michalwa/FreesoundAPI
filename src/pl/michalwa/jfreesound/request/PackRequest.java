package pl.michalwa.jfreesound.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import pl.michalwa.jfreesound.data.Pack;

public class PackRequest extends APIRequest<Pack>
{
	/** The id of the pack */
	private final int id;
	
	/** Initializes a pack request that will request the pack with the specified id */
	public PackRequest(int id)
	{
		this.id = id;
	}
	
	@Override
	protected void prepare(List<String> path, Map<String, String> params)
	{
		path.addAll(Arrays.asList("packs", String.valueOf(id)));
	}
	
	@Override
	public Pack processResponse(JsonObject response, Gson gson)
	{
		return gson.fromJson(response, Pack.class);
	}
}
