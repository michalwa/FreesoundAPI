package pl.michalwa.jfreesound.request.search;

import java.util.List;
import java.util.Map;

/**
 * The text search request type. Used to list sounds matching a certain search query.
 *
 * <p> See: <a href="https://freesound.org/docs/api/resources_apiv2.html#text-search">API documentation page</a>.
 */
public class TextSearch extends SearchRequest
{
	/** The search query */
	private final TextSearchQuery query;
	
	/**
	 * Constructs a text search request with the given search query
	 *
	 * @param query the query to use with the text search
	 */
	public TextSearch(TextSearchQuery query)
	{
		this.query = query;
	}
	
	@Override
	protected void prepare(List<String> path, Map<String, String> params)
	{
		super.prepare(path, params);
		
		path.add("text");
		params.put("query", query.toString());
	}
	
	/**
	 * The search query
	 */
	public TextSearchQuery query()
	{
		return query;
	}
}
