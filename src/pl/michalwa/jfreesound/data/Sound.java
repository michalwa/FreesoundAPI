package pl.michalwa.jfreesound.data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import pl.michalwa.jfreesound.request.SimilarSounds;

/** The response data structure of the
 * {@link pl.michalwa.jfreesound.request.SoundRequest SoundRequest} */
@SuppressWarnings({"unused", "unchecked"})
public class Sound
{
	/* The names of these fields correspond to
	 * the JSON properties of the API response.
	 * They must not be changed for Gson uses
	 * those names to deserialize JSON objects */
	private int id = 0;
	private String url = null;
	private String name = null;
	private Set<String> tags = Collections.EMPTY_SET;
	private String description = null;
	private String geotag = null;
	private String created = null;
	private String license = null;
	private String type = null;
	private int channels = 0;
	private int filesize = 0;
	private int bitrate = 0;
	private int bitdepth = 0;
	private int duration = 0;
	private int samplerate = 0;
	private String username = null;
	private String pack = null;
	private String download = null;
	private String bookmark = null;
	private Map<String, String> previews = null;
	private Map<String, String> images = null;
	private int num_downloads = 0;
	private float avg_ratings = 0.0f;
	private int num_ratings = 0;
	private String rate = null;
	private String comments = null;
	private int num_comments = 0;
	private String comment = null;
	private String similar_sounds = null;
	private Object analysis = null;
	private String analysis_stats = null;
	private String analysis_frames = null;

	/** The sound's ID
	 * @return 'id' field value */
	public int id()
	{
		return id;
	}

	/** URL on the freesound.org website to view the sound
	 * @return 'url' field value */
	public String url()
	{
		return url;
	}

	/** Name of the sound
	 * @return 'name' field value */
	public String name()
	{
		return name;
	}

	/** A set of tags assigned to this sound
	 * @return 'tags' field value */
	public Set<String> tags()
	{
		return Collections.unmodifiableSet(tags);
	}

	/** The description of the sound
	 * @return 'description' field value */
	public String description()
	{
		return description;
	}

	/** The geotag assigned to this sound
	 * @return 'geotag' field value */
	public Geotag geotag()
	{
		return Optional.ofNullable(geotag).map(Geotag::new).orElse(null);
	}

	/** The DateTime when the sound was created
	 * @return 'created' field value */
	public LocalDateTime created()
	{
		return LocalDateTime.parse(created);
	}

	/** The license under which the sound is available
	 * @return 'license' field value */
	public String license()
	{
		return license;
	}

	/** The type of the sound (wav, aif, aiff, mp3, m4a or flac)
	 * @return 'type' field value */
	public Type type()
	{
		return Stream.of(Type.values())
				.filter(t -> t.type.equals(type))
				.findFirst()
				.orElse(null);
	}

	/** The number of channels that the sound has
	 * @return 'channels' field value */
	public int channels()
	{
		return channels;
	}

	/** Size of the file where the sound is stored (in bytes)
	 * @return 'filesize' field value */
	public int fileSize()
	{
		return filesize;
	}

	/** Bitrate of the sound
	 * @return 'bitrate' field value */
	public int bitrate()
	{
		return bitrate;
	}

	/** Bit depth of the sound
	 * @return 'bitdepth' field value */
	public int bitDepth()
	{
		return bitdepth;
	}

	/** Duration of the sound, in seconds
	 * @return 'duration' field value */
	public int duration()
	{
		return duration;
	}

	/** The sample rate of the sound
	 * @return 'samplerate' field value */
	public int sampleRate()
	{
		return samplerate;
	}

	/** Name of the author of the sound
	 * @return 'username' field value */
	public String authorName()
	{
		return username;
	}

	/** URL of the pack the sound is a part of (API resource)
	 * @return 'pack' field value */
	public String packUrl()
	{
		return pack;
	}

	/** URL to download the sound
	 * @return 'download' field value */
	public String downloadUrl()
	{
		return download;
	}

	/** URL for bookmarking the sound
	 * @return 'bookmark' field value */
	public String bookmarkUrl()
	{
		return bookmark;
	}

	/** Returns a URL pointing to the sound preview of the specified type
	 * @return 'previews' field value associated with the given {@link Sound.Preview} key */
	public String previewUrl(Preview type)
	{
		if(previews == null) return null;
		return previews.get(type.key);
	}

	/** Returns a URL pointing to the visualization image of the sound of the specified type
	 * @return 'images' field value associated with the given {@link Sound.Image} key */
	public String imageUrl(Image type)
	{
		if(images == null) return null;
		return images.get(type.key);
	}

	/** Number of downloads
	 * @return 'num_downloads' field value */
	public int numDownloads()
	{
		return num_downloads;
	}

	/** The average rating of the sound
	 * @return 'avg_ratings' field value */
	public float averageRatings()
	{
		return avg_ratings;
	}

	/** Number of ratings
	 * @return 'num_ratings' field value */
	public int numRatings()
	{
		return num_ratings;
	}

	/** URL for rating the sound
	 * @return 'rate' field value */
	public String rateUrl()
	{
		return rate;
	}

	/** URL of a paginated list of the comments of the sound
	 * @return 'comments' field value */
	public String commentsUrl()
	{
		return comments;
	}

	/** Number of comments
	 * @return 'num_comments' field value */
	public int numComments()
	{
		return num_comments;
	}

	/** URL to comment the sound
	 * @return 'comment' field value */
	public String commentUrl()
	{
		return comment;
	}
	
	/** Returns a request that can be used to retrieve similar sounds based on this sound.
	 * The 'similar_sounds' sound info field is not needed for this method to work. */
	public SimilarSounds similarSounds()
	{
		return new SimilarSounds(id);
	}

	/** Requested descriptors information or `null` if no descriptors have been specified in the request
	 * @return 'analysis' field value */
	public Object analysis()
	{
		return analysis;
	}

	/** URL pointing to complete analysis results
	 * @return 'analysis_stats' field value */
	public String analysisStatsUrl()
	{
		return analysis_stats;
	}

	/** URL for retrieving analysis information for each frame of the sound
	 * @return 'analysis_frames' field value */
	public String analysisFramesUrl()
	{
		return analysis_frames;
	}
	
	@Override
	public String toString()
	{
		return "Sound[" + id() + "](" + name() + ")";
	}
	
	/** Enum containing all known sound types */
	public enum Type
	{
		WAV("wav"),
		AIF("aif"),
		AIFF("aiff"),
		MP3("mp3"),
		M4A("m4a"),
		FLAC("flac");

		private String type;

		Type(String type)
		{
			this.type = type;
		}
	}

	/** Supported types of sound previews */
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

		private String key;

		Preview(String key)
		{
			this.key = key;
		}
	}

	/** Sound visualization image types */
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

		private String key;

		Image(String key)
		{
			this.key = key;
		}
	}
}
