package pl.michalwa.jfreesound.request;

import java.util.List;
import java.util.Map;

/** Requests similar sounds to a sound with a given id. See:
 * <a href="https://freesound.org/docs/api/resources_apiv2.html#similar-sounds">API documentation page</a>. */
public class SimilarSounds extends SoundListRequest
{
	/** The id of the sound */
	private final int id;
	
	public SimilarSounds(int id)
	{
		this.id = id;
	}
	
	@Override
	protected void prepare(List<String> path, Map<String, String> params)
	{
		path.add("sounds");
		path.add(String.valueOf(id));
		path.add("similar");
	}
}
