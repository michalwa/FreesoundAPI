package pl.michalwa.jfreesound.request;

/** Requests similar sounds to a sound with a given id */
public class SimilarSoundsRequest extends SoundListRequest
{
	/** The id of the sound */
	private int id;
	
	public SimilarSoundsRequest(int id)
	{
		this.id = id;
	}
	
	@Override
	protected String uri()
	{
		return joinURL("sounds", id, "similar");
	}
}
