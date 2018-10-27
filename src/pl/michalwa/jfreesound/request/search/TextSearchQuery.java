package pl.michalwa.jfreesound.request.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/** The search query class used by the
 * {@link TextSearch}. */
public class TextSearchQuery
{
	/** Search terms marked as 'included'. The sound has to
	 * contain the included terms in order to satisfy the query. */
	private final List<String> included;
	/** Search terms marked as 'excluded'. The sound must not
	 * contain the excluded terms in order to satisfy the query. */
	private final List<String> excluded;
	
	/** Constructs an empty text search query. An empty
	 * query matches all sounds by default. */
	public TextSearchQuery()
	{
		included = new ArrayList<>();
		excluded = new ArrayList<>();
	}
	
	/** Constructs a query only the sounds containing the given phrases satisfy. */
	public TextSearchQuery(String... included)
	{
		this();
		this.included.addAll(Arrays.asList(included));
	}
	
	/** Constructs a query with the given included and excluded terms */
	public TextSearchQuery(List<String> included, List<String> excluded)
	{
		this.included = included;
		this.excluded = excluded;
	}
	
	/** Sets this query to include the given term.
	 * @return this */
	public TextSearchQuery include(String term)
	{
		excluded.remove(term);
		included.add(term);
		return this;
	}
	
	/** Sets this query to exclude the given term.
	 * @return this */
	public TextSearchQuery exclude(String term)
	{
		included.remove(term);
		excluded.add(term);
		return this;
	}
	
	/** Shorthand method, returns {@code new TextSearch(this)} */
	public TextSearch search()
	{
		return new TextSearch(this);
	}
	
	/** Renders the query to a string. */
	@Override
	public String toString()
	{
		StringJoiner builder = new StringJoiner(" ");
		for(String s : included) builder.add("+\"" + s + "\"");
		for(String s : excluded) builder.add("-\"" + s + "\"");
		return builder.toString();
	}
}
