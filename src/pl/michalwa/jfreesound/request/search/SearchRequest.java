package pl.michalwa.jfreesound.request.search;

import java.util.List;
import java.util.Map;
import pl.michalwa.jfreesound.request.SoundListRequest;

/** Base search request class */
public abstract class SearchRequest extends SoundListRequest
{
	/** Seach sorting order */
	private Sorting sorting = null;
	
	/** Sets the sorting */
	public SearchRequest sortWith(Sorting sorting)
	{
		this.sorting = sorting;
		return this;
	}
	
	@Override
	protected void prepare(List<String> path, Map<String, String> params)
	{
		super.prepare(path, params);
		if(sorting != null) params.put("sort", sorting.toString());
		path.add("search");
	}
}
