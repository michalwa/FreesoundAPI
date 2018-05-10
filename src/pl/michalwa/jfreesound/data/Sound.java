package pl.michalwa.jfreesound.data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

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

	/** The sound's ID */
	public int id()
	{
		return id;
	}

	/** URL on the freesound.org website to view the sound */
	public String url()
	{
		return url;
	}

	/** Name of the sound */
	public String name()
	{
		return name;
	}

	/** A set of tags assigned to this sound */
	public Set<String> tags()
	{
		return Collections.unmodifiableSet(tags);
	}

	/** The description of the sound */
	public String description()
	{
		return description;
	}

	/** The geotag assigned to this sound */
	public Geotag geotag()
	{
		return Optional.ofNullable(geotag).map(Geotag::new).orElse(null);
	}

	/** The DateTime when the sound was created */
	public LocalDateTime created()
	{
		return LocalDateTime.parse(created);
	}

	/** The license under which the sound is available */
	public String license()
	{
		return license;
	}

	/** The type of the sound (wav, aif, aiff, mp3, m4a or flac) */
	public Type type()
	{
		return Stream.of(Type.values())
				.filter(t -> t.type.equals(type))
				.findFirst()
				.orElse(null);
	}

	/** The number of channels that the sound has */
	public int channels()
	{
		return channels;
	}

	/** Size of the file where the sound is stored (in bytes) */
	public int fileSize()
	{
		return filesize;
	}

	/** Bitrate of the sound */
	public int bitrate()
	{
		return bitrate;
	}

	/** Bit depth of the sound */
	public int bitDepth()
	{
		return bitdepth;
	}

	/** Duration of the sound, in seconds */
	public int duration()
	{
		return duration;
	}

	/** The sample rate of the sound */
	public int sampleRate()
	{
		return samplerate;
	}

	/** Name of the author of the sound */
	public String authorName()
	{
		return username;
	}

	/** URL of the pack the sound is a part of (API resource) */
	public String packUrl()
	{
		return pack;
	}

	/** URL to download the sound */
	public String downloadUrl()
	{
		return download;
	}

	/** URL for bookmarking the sound */
	public String bookmark()
	{
		return bookmark;
	}

	/** Returns a URL pointing to the sound
	 * preview of the specified type */
	public String previewUrl(Preview type)
	{
		if(previews == null) return null;
		return previews.get(type.key);
	}

	/** Returns a URL pointing to the visualization
	 * image of the sound of the specified type */
	public String imageUrl(Image type)
	{
		if(images == null) return null;
		return images.get(type.key);
	}

	/** Number of downloads */
	public int numDownloads()
	{
		return num_downloads;
	}

	/** The average rating of the sound */
	public float averageRatings()
	{
		return avg_ratings;
	}

	/** Number of ratings */
	public int numRatings()
	{
		return num_ratings;
	}

	/** URL for rating the sound */
	public String rateUrl()
	{
		return rate;
	}

	/** URL of a paginated list of the comments of the sound */
	public String commentsUrl()
	{
		return comments;
	}

	/** Number of comments */
	public int numComments()
	{
		return num_comments;
	}

	/** URL to comment the sound */
	public String commentUrl()
	{
		return comment;
	}

	/** URL pointing to the similarity resource
	 * (to get a list of similar sounds) */
	public String similarSoundsUrl()
	{
		return similar_sounds;
	}

	/** Requested descriptors information or `null`
	 * if no descriptors have been specified in the request */
	public Object analysis()
	{
		return analysis;
	}

	/** URL pointing to complete analysis results */
	public String analysisStatsUrl()
	{
		return analysis_stats;
	}

	/** URL for retrieving analysis information
	 * for each frame of the sound */
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
