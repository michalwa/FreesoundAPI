package pl.michalwa.jfreesound.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/** A utility class that allows conviniently building maps */
public class MapBuilder<K, V>
{
	private Map<K, V> map;

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
	
	/** Builds and returns the map */
	public Map<K, V> build()
	{
		return map;
	}
	
	/** Builds and returns the map as an unmodifiable map */
	public Map<K, V> buildUnmodifiable()
	{
		return Collections.unmodifiableMap(map);
	}
}
