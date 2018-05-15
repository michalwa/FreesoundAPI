package pl.michalwa.jfreesound.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/** A utility class that allows conviniently building maps */
public class MapBuilder<K, V>
{
	private Map<K, V> map;
	private boolean unmodifiable;

	/** Initializes an empty map builder */
	public MapBuilder()
	{
		map = new HashMap<>();
	}
	
	/** Puts a value in the map */
	public MapBuilder<K, V> put(K key, V value)
	{
		map.put(key, value);
		return this;
	}
	
	/** Sets the map to be built as unmodifable, if the parameter
	 * <code>unmodifiable</code> is <code>true</code>. If <code>false</code>
	 * is given, the built map will be modifiable. */
	public MapBuilder<K, V> unmodifiable(boolean unmodifiable)
	{
		this.unmodifiable = unmodifiable;
		return this;
	}
	
	/** Builds and returns the map */
	public Map<K, V> build()
	{
		return unmodifiable ? Collections.unmodifiableMap(map) : map;
	}
}
