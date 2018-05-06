package pl.michalwa.jfreesound.request;

/** A basic request implementation. Should only
 * be used for testing. */
public class SimpleAPIRequest extends APIRequest
{
	/** Parts of the request URL */
	private Object[] parts;
	
	/** Constructs a simple request with the given sub-url */
	public SimpleAPIRequest(String url)
	{
		parts = new String[] { url };
	}
	
	/** Constructs a simple request from the given sub-url parts */
	public SimpleAPIRequest(Object... url)
	{
		parts = url;
	}
	
	@Override
	protected String url()
	{
		return joinURL(parts);
	}
}
