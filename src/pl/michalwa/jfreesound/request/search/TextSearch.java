package pl.michalwa.jfreesound.request.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import pl.michalwa.jfreesound.request.SoundListRequest;

/** The text search request type. Used to list sounds matching a certain search query. See:
 * <a href="https://freesound.org/docs/api/resources_apiv2.html#text-search">API documentation page</a>. */
public class TextSearch extends SoundListRequest
{
	/** The search query */
	public TextSearchQuery query;
	/** The fields to include in the results */
	private List<String> fields = new ArrayList<>();
	
	/** Constructs a text search request with the given search query */
	public TextSearch(TextSearchQuery query)
	{
		this.query = query;
	}
	
	/** Sets this text search request to include all fields
	 * with the given names in the results. */
	public TextSearch includeFields(String... fields)
	{
		this.fields.addAll(Arrays.asList(fields));
		return this;
	}
	
	@Override
	protected void prepare(List<String> path, Map<String, String> params)
	{
		path.add("search");
		path.add("text");
		params.put("query", query.toString());
		if(!fields.isEmpty()) params.put("fields", String.join(",", fields));
	}
}
