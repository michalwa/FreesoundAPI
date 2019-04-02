package pl.michalwa.jfreesound.data;

import java.time.LocalDateTime;
import pl.michalwa.jfreesound.request.PackSoundsRequest;

/** Sound pack instance data structure */
@SuppressWarnings({"FieldCanBeLocal"})
public class Pack
{
	/* NOTE: The names of these fields correspond to the JSON properties of the API response.
	 * They must not be changed for Gson uses those names to deserialize JSON objects
	 * They must also be non-final. */
	private int id = -1;
	private String url = null;
	private String description = null;
	private String created = null;
	private String name = null;
	private String username = null;
	private int num_sounds = -1;
	private int num_downloads = -1;
	
	/** The id of the pack */
	public int id()
	{
		if(id == -1) throw new FieldNotInitializedException(getClass(), "id");
		return id;
	}
	
	/** URL of this pack on the Freesound website */
	public String url()
	{
		if(url == null) throw new FieldNotInitializedException(getClass(), "url");
		return url;
	}
	
	/** The description of the pack */
	public String description()
	{
		if(description == null) throw new FieldNotInitializedException(getClass(), "description");
		return description;
	}
	
	/** The date when the pack was created */
	public LocalDateTime dateCreated()
	{
		if(created == null) throw new FieldNotInitializedException(getClass(), "created");
		return LocalDateTime.parse(created);
	}
	
	/** Name of the pack */
	public String name()
	{
		if(name == null) throw new FieldNotInitializedException(getClass(), "name");
		return name;
	}
	
	/** The username of the creator of the pack */
	public String username()
	{
		if(username == null) throw new FieldNotInitializedException(getClass(), "username");
		return username;
	}
	
	/** Number of sounds in the pack */
	public int numSounds()
	{
		if(num_sounds == -1) throw new FieldNotInitializedException(getClass(), "num_sounds");
		return num_sounds;
	}
	
	/** Returns a request for a list of sounds included in the pack */
	public PackSoundsRequest sounds()
	{
		return new PackSoundsRequest(id());
	}
	
	/** Number of times this pack has been downloaded */
	public int numDownloads()
	{
		if(num_downloads == -1) throw new FieldNotInitializedException(getClass(), "num_downloads");
		return num_downloads;
	}
	
	@Override
	public String toString()
	{
		return "Pack" + (id != -1 ? " #" + id : "") + (name != null ? " \"" + name + "\"" : "");
	}
}
