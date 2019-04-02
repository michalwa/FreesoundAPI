package pl.michalwa.jfreesound.http;

/**
 * A wrapper interface for an HTTP response from the {@link HttpClient}
 */
public interface HttpResponse
{
	/**
	 * Reads the request body and returns it
	 */
	String body();
}
