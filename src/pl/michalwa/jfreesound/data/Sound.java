package pl.michalwa.jfreesound.data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import pl.michalwa.jfreesound.request.APIRequest;
import pl.michalwa.jfreesound.request.PackRequest;
import pl.michalwa.jfreesound.request.SimilarSounds;

/**
 * A representation of the sound API resource
 */
@SuppressWarnings({"FieldCanBeLocal"})
public class Sound
{
	/* NOTE: The names of these fields correspond to the JSON properties of the API response.
	 * They must not be changed for Gson uses those names to deserialize JSON objects.
	 * They must also be non-final. */
	private int id = -1;
	private String url = null;
	private String name = null;
	private Set<String> tags = null;
	private String description = null;
	private String geotag = null;
	private String created = null;
	private String license = null;
	private String type = null;
	private int channels = -1;
	private int filesize = -1;
	private int bitrate = -1;
	private int bitdepth = -1;
	private int duration = -1;
	private int samplerate = -1;
	private String username = null;
	private String pack = null;
	private String download = null;
	private String bookmark = null;
	private Map<String, String> previews = null;
	private Map<String, String> images = null;
	private int num_downloads = -1;
	private float avg_ratings = -1.0f;
	private int num_ratings = -1;
	private String rate = null;
	private String comments = null;
	private int num_comments = -1;
	private String comment = null;
	private String analysis_stats = null;
	private String analysis_frames = null;

	/** The sound's ID */
	public int id()
	{
		if(id == -1) throw new FieldNotInitializedException(getClass(), "id");
		return id;
	}

	/** URL on the freesound.org website to view the sound */
	public String url()
	{
		if(url == null) throw new FieldNotInitializedException(getClass(), "url");
		return url;
	}

	/** Name of the sound */
	public String name()
	{
		if(name == null) throw new FieldNotInitializedException(getClass(), "name");
		return name;
	}

	/** A set of tags assigned to this sound */
	public Set<String> tags()
	{
		if(tags == null) throw new FieldNotInitializedException(getClass(), "tags");
		return Collections.unmodifiableSet(tags);
	}

	/** The description of the sound */
	public String description()
	{
		if(description == null) throw new FieldNotInitializedException(getClass(), "description");
		return description;
	}

	/** The geotag assigned to this sound */
	public Geotag geotag()
	{
		if(geotag == null) throw new FieldNotInitializedException(getClass(), "geotag");
		return new Geotag(geotag);
	}

	/** The DateTime when the sound was created */
	public LocalDateTime dateCreated()
	{
		if(created == null) throw new FieldNotInitializedException(getClass(), "created");
		return LocalDateTime.parse(created);
	}

	/** The license under which the sound is available */
	public String license()
	{
		if(license == null) throw new FieldNotInitializedException(getClass(), "license");
		return license;
	}

	/** The type of the sound (wav, aif, aiff, mp3, m4a or flac) */
	public Type type()
	{
		if(type == null) throw new FieldNotInitializedException(getClass(), "type");
		return Stream.of(Type.values())
				.filter(t -> t.type.equals(type))
				.findFirst()
				.orElse(null);
	}

	/** The number of channels that the sound has */
	public int channels()
	{
		if(channels == -1) throw new FieldNotInitializedException(getClass(), "channels");
		return channels;
	}

	/** Size of the file where the sound is stored (in bytes) */
	public int fileSize()
	{
		if(filesize == -1) throw new FieldNotInitializedException(getClass(), "filesize");
		return filesize;
	}

	/** Bitrate of the sound */
	public int bitrate()
	{
		if(bitrate == -1) throw new FieldNotInitializedException(getClass(), "bitrate");
		return bitrate;
	}

	/** Bit depth of the sound */
	public int bitDepth()
	{
		if(bitdepth == -1) throw new FieldNotInitializedException(getClass(), "bitdepth");
		return bitdepth;
	}

	/** Duration of the sound, in seconds */
	public int duration()
	{
		if(duration == -1) throw new FieldNotInitializedException(getClass(), "duration");
		return duration;
	}

	/** The sample rate of the sound */
	public int sampleRate()
	{
		if(samplerate == -1) throw new FieldNotInitializedException(getClass(), "samplerate");
		return samplerate;
	}

	/** Name of the author of the sound */
	public String authorName()
	{
		if(username == null) throw new FieldNotInitializedException(getClass(), "username");
		return username;
	}

	/** Returns a request for the pack this sound belongs to. If the sound does not belong to a pack,
	 * returns {@code null}. */
	public PackRequest pack()
	{
		if(pack == null) throw new FieldNotInitializedException(getClass(), "pack");
		if(pack.isEmpty()) return null;
		
		String packUrl = pack.substring(0, pack.length() - 1);
		int id = Integer.parseInt(packUrl.substring(packUrl.lastIndexOf("/") + 1));
		return new PackRequest(id);
	}

	/** URL to download the sound */
	public String downloadUrl()
	{
		if(download == null) throw new FieldNotInitializedException(getClass(), "download");
		return download;
	}

	/** URL for bookmarking the sound */
	public String bookmarkUrl()
	{
		if(bookmark == null) throw new FieldNotInitializedException(getClass(), "bookmark");
		return bookmark;
	}

	/** Returns a URL pointing to the sound preview of the specified type */
	public String previewUrl(Preview type)
	{
		if(previews == null) throw new FieldNotInitializedException(getClass(), "previews");
		return previews.get(type.key);
	}

	/** Returns a URL pointing to the visualization image of the sound of the specified type */
	public String imageUrl(Image type)
	{
		if(images == null) throw new FieldNotInitializedException(getClass(), "images");
		return images.get(type.key);
	}

	/** Number of downloads */
	public int numDownloads()
	{
		if(num_downloads == -1) throw new FieldNotInitializedException(getClass(), "num_downloads");
		return num_downloads;
	}

	/** The average rating of the sound */
	public float averageRatings()
	{
		if(avg_ratings == -1.0f) throw new FieldNotInitializedException(getClass(), "avg_ratings");
		return avg_ratings;
	}

	/** Number of ratings */
	public int numRatings()
	{
		if(num_ratings == -1) throw new FieldNotInitializedException(getClass(), "num_ratings");
		return num_ratings;
	}

	/** URL for rating the sound */
	public String rateUrl()
	{
		if(rate == null) throw new FieldNotInitializedException(getClass(), "rate");
		return rate;
	}

	/** URL of a paginated list of the comments of the sound */
	public String commentsUrl()
	{
		if(comments == null) throw new FieldNotInitializedException(getClass(), "comments");
		return comments;
	}

	/** Number of comments */
	public int numComments()
	{
		if(num_ratings == -1) throw new FieldNotInitializedException(getClass(), "num_ratings");
		return num_comments;
	}

	/** URL to comment the sound */
	public String commentUrl()
	{
		if(comment == null) throw new FieldNotInitializedException(getClass(), "comment");
		return comment;
	}
	
	/**
	 * Returns a request that can be used to retrieve similar sounds based on this sound.
	 * The 'similar_sounds' sound info field is not needed for this method to work.
	 */
	public SimilarSounds similarSounds()
	{
		return new SimilarSounds(id());
	}

	/**
	 * Requested descriptors information or `null` if no descriptors have been specified in the reques
	 */
	public APIRequest<?> analysis()
	{
		// TODO: Sound analysis request and data structure
		throw new UnsupportedOperationException("Not implemented.");
	}

	/** URL pointing to complete analysis results */
	public String analysisStatsUrl()
	{
		if(analysis_stats == null) throw new FieldNotInitializedException(getClass(), "analysis_stats");
		return analysis_stats;
	}

	/** URL for retrieving analysis information for each frame of the sound */
	public String analysisFramesUrl()
	{
		if(analysis_frames == null) throw new FieldNotInitializedException(getClass(), "analysis_frames");
		return analysis_frames;
	}
	
	@Override
	public String toString()
	{
		return "Sound "
			+ (id != -1 ? "#" + id : "")                  // id
			+ (name != null ? " \"" + name + "\"" : "");  // name
	}
	
	/**
	 * Supported sound types
	 */
	public enum Type
	{
		WAV("wav"),
		AIF("aif"),
		AIFF("aiff"),
		MP3("mp3"),
		M4A("m4a"),
		FLAC("flac");

		private final String type;

		Type(String type)
		{
			this.type = type;
		}
	}

	/**
	 * Supported types of sound previews
	 */
	public enum Preview
	{
		/** ~128kbps quality mp3 preview */
		HIGH_QUALITY_MP3("preview-hq-mp3"),
		/** ~64kbps quality mp3 preview */
		LOW_QUALITY_MP3("preview-lq-mp3"),
		/** ~128kbps quality ogg preview */
		HIGH_QUALITY_OGG("preview-hq-ogg"),
		/** ~64kbps quality ogg preview */
		LOW_QUALITY_OGG("preview-lq-ogg");

		private final String key;

		Preview(String key)
		{
			this.key = key;
		}
	}

	/**
	 * Visualization image types
	 */
	public enum Image
	{
		/** Large waveform image */
		WAVEFORM_LARGE("waveform_l"),
		/** Medium waveform image */
		WAVEFORM_MEDIUM("waveform_m"),
		/** Large spectrogram image */
		SPECTROGRAM_LARGE("spectral_l"),
		/** Medium spectrogram image */
		SPECTROGRAM_MEDIUM("spectral_m");

		private final String key;

		Image(String key)
		{
			this.key = key;
		}
	}
}
