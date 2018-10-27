package pl.michalwa.jfreesound.request;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/** Requests sounds from a specific pack */
public class PackSoundsRequest extends SoundListRequest
{
	/** Id of the pack */
	private final int id;
	
	/** Initializes a new {@link PackSoundsRequest} that will request sounds
	 * from the pack with the specified id */
	public PackSoundsRequest(int id)
	{
		this.id = id;
	}
	
	@Override
	protected void prepare(List<String> path, Map<String, String> params)
	{
		super.prepare(path, params);
		path.addAll(Arrays.asList("packs", String.valueOf(id), "sounds"));
	}
}
