package pl.michalwa.jfreesound.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class that allows conviniently building maps
 */
public class MapBuilder<K, V>
{
	private final Map<K, V> map;
	private boolean unmodifiable;

	/**
	 * Initializes an empty map builder
	 */
	public MapBuilder()
	{
		map = new HashMap<>();
	}
	
	/**
	 * Puts a value in the map
	 *
	 * @param key the key to put the value at
	 * @param value the value to put into the map
	 */
	public MapBuilder<K, V> put(K key, V value)
	{
		map.put(key, value);
		return this;
	}
	
	/**
	 * Sets the map to be built as modifiable
	 */
	public MapBuilder<K, V> modifiable()
	{
		this.unmodifiable = false;
		return this;
	}
	
	/**
	 * Sets the map to be built as unmodifable
	 */
	public MapBuilder<K, V> unmodifiable()
	{
		this.unmodifiable = true;
		return this;
	}
	
	/**
	 * Builds and returns the map
	 */
	public Map<K, V> build()
	{
		return unmodifiable ? Collections.unmodifiableMap(map) : map;
	}
}
