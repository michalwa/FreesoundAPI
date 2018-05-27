package pl.michalwa.jfreesound.request.search;

import java.util.ArrayList;
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
	
	/** Sets this text search request to include the field
	 * with the given name in the results. See what fields you can include in the
	 * <a href="https://freesound.org/docs/api/resources_apiv2.html#sound-instance">API documentation</a>.
	 * @return this */
	public TextSearch includeField(String field)
	{
		fields.add(field);
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
