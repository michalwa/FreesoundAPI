package pl.michalwa.jfreesound.http;

/**
 * An HTTP request with a body entity
 */
public abstract class HttpRequestWithBody extends HttpRequest
{
	/**
	 * Returns the computed body of the request.
	 */
	public abstract String body();
}
