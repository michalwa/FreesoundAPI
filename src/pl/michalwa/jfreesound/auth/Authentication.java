package pl.michalwa.jfreesound.auth;

import org.apache.http.client.methods.HttpGet;

/** Represents an authentication type and mechanism */
public interface Authentication
{
	/** Processes the given HTTP request before execution.
	 * Applies headers, parameters, etc. */
	void processRequest(HttpGet request);
}
