package pl.michalwa.jfreesound.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import pl.michalwa.jfreesound.response.Sound;

/** A simple sound-by-id request */
public class SoundRequest extends APIRequest<Sound>
{
	private int id;
	private Gson gson = new Gson();
	
	/** @param id the id of the requested sound */
	public SoundRequest(int id)
	{
		this.id = id;
	}
	
	@Override
	protected String uri()
	{
		return joinURL("sounds", id);
	}
	
	@Override
	public Sound processResponse(JsonObject response)
	{
		return gson.fromJson(response, Sound.class);
	}
}
