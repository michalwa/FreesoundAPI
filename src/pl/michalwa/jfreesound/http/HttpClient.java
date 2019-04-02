package pl.michalwa.jfreesound.http;

import pl.michalwa.jfreesound.auth.UnauthorizedException;

/**
 * A wrapper for the abstract HTTP client, used to make HTTP requests.
 *
 * <p> The preferred way to implement the http client with your client library of choice
 *     is to implement this interface and then use
 *     {@link pl.michalwa.jfreesound.Freesound#setHttpClient(HttpClient) Freesound.setHttpClient(HttpClient)}.
 */
public interface HttpClient
{
	/**
	 * Executes the given request and returns the response.
	 */
	HttpResponse execute(HttpRequest request) throws HttpException, UnauthorizedException;
}
