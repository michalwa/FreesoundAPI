package pl.michalwa.jfreesound.http;

/**
 * An HTTP request with a body entity
 *
 * @param <T> the type of the request body
 */
public abstract class HttpRequestWithBody<T> extends HttpRequest
{
	/**
	 * Returns the computed body of the request.
	 * Can be {@code null} for request types without a body.
	 */
	public abstract T body();
}
