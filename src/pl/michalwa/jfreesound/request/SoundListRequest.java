package pl.michalwa.jfreesound.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.*;
import pl.michalwa.jfreesound.data.Sound;

/**
 * Base class for all request that return a list of sounds
 */
public abstract class SoundListRequest extends APIRequest<Sound[]>
{
	/** The fields to include in the results */
	private final Set<String> fields = new HashSet<>();
	/** The selected page of the response */
	private int page = 0;
	
	/**
	 * Sets this text search request to include all fields with the given names in the results.
	 *
	 * @param fields the fields to include
	 */
	public SoundListRequest includeFields(String... fields)
	{
		this.fields.addAll(Arrays.asList(fields));
		return this;
	}
	
	/**
	 * Selects the specified page of the response
	 *
	 * @param page the page index
	 */
	public SoundListRequest setPage(int page)
	{
		this.page = page;
		return this;
	}
	
	@Override
	public Sound[] processResponse(JsonObject response, JsonParser json, Gson gson)
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
