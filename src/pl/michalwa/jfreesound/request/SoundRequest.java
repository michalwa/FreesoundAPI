package pl.michalwa.jfreesound.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import pl.michalwa.jfreesound.data.Sound;

/**
 * Requests a sound instance by id
 */
public class SoundRequest extends APIRequest<Sound>
{
	/** The id of the requested sound */
	private final int id;
	
	/**
	 * Constructs a sound request
	 *
	 * @param id the id of the requested sound
	 */
	public SoundRequest(int id)
	{
		this.id = id;
	}
	
	@Override
	protected void prepare(List<String> path, Map<String, String> params)
	{
		path.addAll(Arrays.asList("sounds", String.valueOf(id)));
	}
	
	@Override
	public Sound processResponse(JsonObject response, JsonParser json, Gson gson)
	{
		return gson.fromJson(response, Sound.class);
	}
}
