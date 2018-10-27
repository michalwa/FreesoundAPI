package pl.michalwa.jfreesound.data;

import java.time.LocalDateTime;
import java.util.Map;
import pl.michalwa.jfreesound.request.APIRequest;
import pl.michalwa.jfreesound.request.SoundListRequest;
import pl.michalwa.jfreesound.request.UserSoundsRequest;

/** The user instance data structure */
@SuppressWarnings({"FieldCanBeLocal"})
public class User
{
	/* NOTE: The names of these fields correspond to the JSON properties of the API response.
	 * They must not be changed for Gson uses those names to deserialize JSON objects */
	private String url = null;
	private String username = null;
	private String about = null;
	private String home_page = null;
	private Map<String, String> avatar = null;
	private String date_joined = null;
	private int num_sounds = -1;
	private int num_packs = -1;
	private int num_posts = -1;
	private int num_comments = -1;
	
	/** The url of this user's profile on the Freesound website. */
	public String profileUrl()
	{
		if(url == null) throw new FieldNotInitializedException(getClass(), "url");
		return url;
	}
	
	/** The user's username */
	public String username()
	{
		if(username == null) throw new FieldNotInitializedException(getClass(), "username");
		return username;
	}
	
	/** The 'about' text from the user's profile */
	public String about()
	{
		if(about == null) throw new FieldNotInitializedException(getClass(), "about");
		return about;
	}
	
	/** The user's homepage outside Freesound */
	public String homepageUrl()
	{
		if(home_page == null) throw new FieldNotInitializedException(getClass(), "home_page");
		return home_page;
	}
	
	/** Url of the user's avatar image for the specified size */
	public String avatarUrl(AvatarSize size)
	{
		if(avatar == null) throw new FieldNotInitializedException(getClass(), "avatar");
		return avatar.get(size.key);
	}
	
	/** The date when the user joined Freesound */
	public LocalDateTime dateJoined()
	{
		if(date_joined == null) throw new FieldNotInitializedException(getClass(), "date_joined");
		return LocalDateTime.parse(date_joined);
	}
	
	/** Number of sounds uploaded by the user */
	public int numSounds()
	{
		if(num_sounds == -1) throw new FieldNotInitializedException(getClass(), "num_sounds");
		return num_sounds;
	}
	
	/** Returns a new {@link UserSoundsRequest} that will request sounds uploaded by this user */
	public SoundListRequest sounds()
	{
		return new UserSoundsRequest(username());
	}
	
	/** Number of packs created by the user */
	public int numPacks()
	{
		if(num_packs == -1) throw new FieldNotInitializedException(getClass(), "num_packs");
		return num_packs;
	}
	
	/** Retruns a request for the user's bookmark categories */
	public APIRequest<?> packs()
	{
		// TODO: Packs request and data structure
		throw new UnsupportedOperationException("Not implemented.");
	}
	
	/** Number of forum posts by the user */
	public int numPosts()
	{
		if(num_posts == -1) throw new FieldNotInitializedException(getClass(), "num_posts");
		return num_posts;
	}
	
	/** Number of commens the user has made on other users' sounds */
	public int numCommens()
	{
		if(num_comments == -1) throw new FieldNotInitializedException(getClass(), "num_comments");
		return num_comments;
	}
	
	/** Retruns a request for the user's bookmark categories */
	public APIRequest<?> bookmarkCategories()
	{
		// TODO: Bookmark categories request and data structure
		throw new UnsupportedOperationException("Not implemented.");
	}
	
	@Override
	public String toString()
	{
		return "User" + (username != null ? " \"" + username + "\"" : "");
	}
	
	/** Supported avatar image sizes */
	public enum AvatarSize
	{
		SMALL("small"),
		MEDIUM("medium"),
		LARGE("large");
		
		private String key;
		
		AvatarSize(String key)
		{
			this.key = key;
		}
	}
}
