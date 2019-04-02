package pl.michalwa.jfreesound.auth;

import pl.michalwa.jfreesound.http.HttpRequest;

/**
 * Authentication method base class.
 *
 * <p> Subclasses of this class allow performing authentication and their instances can
 *     then be used to process outcoming requests so that they contain authentication information.
 */
public interface Authentication
{
	/**
	 * Processes the given HTTP request before execution. Adds headers containing
	 * authentication information.
	 *
	 * @param request the request to be processed
	 */
	void processRequest(HttpRequest request);
}
