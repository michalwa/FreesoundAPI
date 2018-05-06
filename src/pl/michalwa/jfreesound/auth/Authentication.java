package pl.michalwa.jfreesound.auth;

import org.apache.http.client.methods.HttpUriRequest;

/** Represents an authentication type and method */
public interface Authentication
{
	/** Processes the given HTTP request before execution.
	 * Applies headers, parameters, etc. */
	void processRequest(HttpUriRequest request);
}
