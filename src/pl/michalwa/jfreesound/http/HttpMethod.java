package pl.michalwa.jfreesound.http;

/**
 * HTTP request methods used and supported by the library
 */
public enum HttpMethod
{
	GET("GET"),
	POST("POST");
	
	private final String value;
	
	HttpMethod(String value)
	{
		this.value = value;
	}
	
	public String value()
	{
		return value;
	}
}
