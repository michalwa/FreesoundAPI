package pl.michalwa.jfreesound.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

/** {@link HttpPost} request builder */
public class HttpPostBuilder
{
	/** Post request parameters */
	private List<NameValuePair> params;
	/** The request URI */
	private String uri;
	
	/** Constructs a new {@link HttpPostBuilder} with
	 * the given URI. */
	public HttpPostBuilder(String uri)
	{
		this.uri = uri;
		params = new ArrayList<>();
	}
	
	/** Adds a parameter to the request */
	public HttpPostBuilder param(String name, Object value)
	{
		params.add(new BasicNameValuePair(name, value.toString()));
		return this;
	}
	
	/** Builds the {@link HttpPost} request instance */
	public HttpPost build()
	{
		HttpPost post = new HttpPost(uri);
		if(!params.isEmpty()) {
			try {
				post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			} catch(UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return post;
	}
}
