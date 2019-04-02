package pl.michalwa.jfreesound.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import pl.michalwa.jfreesound.data.User;

/**
 * Requests a user instance by their username
 */
public class UserRequest extends APIRequest<User>
{
	/** The user's username */
	private final String username;
	
	/**
	 * Constructs a new user request that will request the user with the specified username
	 *
	 * @param username the user's username
	 */
	public UserRequest(String username)
	{
		this.username = username;
	}
	
	@Override
	protected void prepare(List<String> path, Map<String, String> params)
	{
		path.addAll(Arrays.asList("users", username));
	}
	
	@Override
	public User processResponse(JsonObject response, JsonParser json, Gson gson)
	{
		return gson.fromJson(response, User.class);
	}
}
