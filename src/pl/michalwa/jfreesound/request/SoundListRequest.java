package pl.michalwa.jfreesound.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import pl.michalwa.jfreesound.data.Sound;

/** Base class for all request that return a list of sounds */
public abstract class SoundListRequest extends APIRequest<Sound[]>
{
	private final Gson gson = new Gson();
	
	@Override
	public Sound[] processResponse(JsonObject response)
	{
		return gson.fromJson(response.get("results"), Sound[].class);
	}
}
