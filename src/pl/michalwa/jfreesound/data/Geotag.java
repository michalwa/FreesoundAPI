package pl.michalwa.jfreesound.data;

/**
 * A geotag that can be assigned to a sound
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
	
	public double latitude()
	{
		return latitude;
	}
	
	public double longitude()
	{
		return longitude;
	}
	
	@Override
	public String toString()
	{
		return "Geotag (" + latitude + ", " + longitude + ")";
	}
}
