package pl.michalwa.jfreesound.data;

/** Thrown when an uninitialized field is trying to be accessed on a data structure object.
 * For example, when calling {@code Sound.geotag()}, when the {@code geotag} field hasn't been requested. */
public class FieldNotInitializedException extends RuntimeException
{
	/** Constructs a new field-not-initialized exception with the given class as the data
	 * structure class in which the exception has occured and the name of the field that caused it. */
	public FieldNotInitializedException(Class<?> dataClass, String fieldName)
	{
		super("Field " + dataClass.getSimpleName() + "." + fieldName + " not initialized. Make sure you include the field in your request.");
	}
}
