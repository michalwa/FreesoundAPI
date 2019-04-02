package pl.michalwa.jfreesound.http;

/**
 * A GET-type HTTP request
 */
public class HttpGetRequest extends HttpRequest
{
	@Override
	public HttpMethod method()
	{
		return HttpMethod.GET;
	}
}
