package pl.michalwa.jfreesound.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.*;
import pl.michalwa.jfreesound.data.Sound;
import pl.michalwa.jfreesound.request.search.SearchRequest;

/** Base class for all request that return a list of sounds */
public abstract class SoundListRequest extends APIRequest<Sound[]>
{
	private final Gson gson = new Gson();
	/** The fields to include in the results */
	private final Set<String> fields = new HashSet<>();
	/** The selected page of the response */
	private int page = 0;
	
	/** Sets this text search request to include all fields
	 * with the given names in the results. */
	public SoundListRequest includeFields(String... field)
	{
		this.fields.addAll(Arrays.asList(field));
		return this;
	}
	
	/** Selects the specified page of the response */
	public SoundListRequest setPage(int page)
	{
		this.page = page;
		return this;
	}
	
	@Override
	public Sound[] processResponse(JsonObject response)
	{
		return gson.fromJson(response.get("results"), Sound[].class);
	}
	
	@Override
	protected void prepare(List<String> path, Map<String, String> params)
	{
		if(!fields.isEmpty()) params.put("fields", String.join(",", fields));
		if(page > 0) params.put("page", String.valueOf(page));
	}
}
