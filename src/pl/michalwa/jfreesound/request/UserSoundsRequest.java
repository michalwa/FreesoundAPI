package pl.michalwa.jfreesound.request;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/** Requests a list of sounds uploaded by a specific user */
public class UserSoundsRequest extends SoundListRequest
{
	/** The username of the user */
	private final String username;
	
	/** Initializes a user sounds request that will request a list of sounds
	 * uploaded by the user with the specified username */
	public UserSoundsRequest(String username)
	{
		this.username = username;
	}
	
	@Override
	protected void prepare(List<String> path, Map<String, String> params)
	{
		super.prepare(path, params);
		path.addAll(Arrays.asList("users", username, "sounds"));
	}
}
