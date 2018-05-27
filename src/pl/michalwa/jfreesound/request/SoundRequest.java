package pl.michalwa.jfreesound.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import pl.michalwa.jfreesound.data.Sound;

/** A simple sound-by-id request */
public class SoundRequest extends APIRequest<Sound>
{
	private int id;
	private final Gson gson = new Gson();
	
	/** @param id the id of the requested sound */
	public SoundRequest(int id)
	{
		this.id = id;
	}
	
	@Override
	protected void prepare(List<String> path, Map<String, String> params)
	{
		path.add("sounds");
		path.add(String.valueOf(id));
	}
	
	@Override
	public Sound processResponse(JsonObject response)
	{
		return gson.fromJson(response, Sound.class);
	}
}
