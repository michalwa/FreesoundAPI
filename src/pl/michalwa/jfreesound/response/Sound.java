package pl.michalwa.jfreesound.response;

import java.util.Map;

public class Sound
{
	public int id;
	public String url;
	public String name;
	public String[] tags;
	public String description;
	public String geotag;
	public String created;
	public String license;
	public String type;
	public int channels;
	public int filesize;
	public int bitrate;
	public int bitdepth;
	public int duration;
	public int samplerate;
	public String username;
	public String pack;
	public String download;
	public String bookmark;
	public Map<String, String> previews;
	public Map<String, String> images;
	public int num_downloads;
	public float avg_ratings;
	public int num_ratings;
	public String rate;
	public String comments;
	public int num_comments;
	public String comment;
	public String similar_sounds;
	public Object analysis;
	public String analysis_stats;
	public String analysis_frames;
}
