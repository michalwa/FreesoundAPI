package pl.michalwa.jfreesound.request.search;

/**
 * Defines a search request sorting
 */
public class Sorting
{
	/**
	 * The actual {@code sort} parameter value
	 */
	private final String sort;
	
	/**
	 * Constructs a default sorting order
	 */
	public Sorting()
	{
		sort = "score";
	}
	
	/**
	 * Constructs a sorting order with the given parameters
	 *
	 * @param criterion the criterion to sort by
	 * @param order the order to sort in
	 */
	public Sorting(Criterion criterion, Order order)
	{
		this.sort = criterion.id + "_" + order.id;
	}
	
	/**
	 * Returns the computed sorting parameter value
	 */
	@Override
	public String toString()
	{
		return sort;
	}
	
	/**
	 * A criterion based on which sorting is performed
	 */
	public enum Criterion
	{
		/** Duration of a sound */
		DURATION("duration"),
		/** Creation date (time) of a sound */
		CREATED("created"),
		/** Number of downloads of a sound */
		DOWNLOADS("downloads"),
		/** Sound rating */
		RATING("rating");
		
		private final String id;
		
		Criterion(String id)
		{
			this.id = id;
		}
	}
	
	/**
	 * Sorting order
	 */
	public enum Order
	{
		DESCENDING("desc"),
		ASCENDING("asc");
		
		private final String id;
		
		Order(String id)
		{
			this.id = id;
		}
	}
}
