package pl.michalwa.jfreesound.data;

/**
 * A geotag that can be read from a sound.
 *
 * <p> Geotags are metadata added by users to their sounds.
 *     This class is a representation of the data type used to store this information.
 */
public class Geotag
{
	private final double latitude, longitude;
	
	/**
	 * Constructs a geotag.
	 *
	 * @param latitude the latitude of the tag
	 * @param longitude the longitude of the tag
	 */
	public Geotag(double latitude, double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * Parses the geotag from a string when the latitude and longitude are separated by a space.
	 *
	 * @param str the geotag string to parse
	 */
	public Geotag(String str)
	{
		String[] vals = str.split(" ");
		latitude = Double.parseDouble(vals[0]);
		longitude = Double.parseDouble(vals[1]);
	}
	
	/** The latitude of the geotag's location. */
	public double latitude()
	{
		return latitude;
	}
	
	/** The longitude of the geotag's location. */
	public double longitude()
	{
		return longitude;
	}
	
	@Override
	public String toString()
	{
		return "Geotag(" + latitude + ", " + longitude + ")";
	}
}
