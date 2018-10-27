package pl.michalwa.jfreesound.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import pl.michalwa.jfreesound.data.User;

/** Requests a user instance */
public class UserRequest extends APIRequest<User>
{
	/** The user's username */
	private final String username;
	
	/** Initializes a new user request that will request the user with the specified username */
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
	public User processResponse(JsonObject response, Gson gson)
	{
		return gson.fromJson(response, User.class);
	}
}
