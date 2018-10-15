package pl.michalwa.jfreesound.request.search;

import java.util.*;
import pl.michalwa.jfreesound.request.SoundListRequest;

/** Base search request class */
public abstract class SearchRequest extends SoundListRequest
{
	/** Seach sorting order */
	private Sorting sorting = null;
	/** The fields to include in the results */
	private Set<String> fields = new HashSet<>();
	
	/** Sets this text search request to include all fields
	 * with the given names in the results. */
	public SearchRequest includeFields(String... field)
	{
		this.fields.addAll(Arrays.asList(field));
		return this;
	}
	
	/** Sets the sorting */
	public SearchRequest sortWith(Sorting sorting)
	{
		this.sorting = sorting;
		return this;
	}
	
	@Override
	protected void prepare(List<String> path, Map<String, String> params)
	{
		if(!fields.isEmpty()) params.put("fields", String.join(",", fields));
		if(sorting != null) params.put("sort", sorting.toString());
		path.add("search");
	}
}
