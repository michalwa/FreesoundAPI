import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import pl.michalwa.jfreesound.Freesound;
import pl.michalwa.jfreesound.data.Sound;
import pl.michalwa.jfreesound.request.SimilarSounds;
import pl.michalwa.jfreesound.request.SimpleRequest;
import pl.michalwa.jfreesound.request.SoundRequest;
import pl.michalwa.jfreesound.request.search.TextSearchQuery;
import pl.michalwa.jfreesound.request.search.TextSearch;

import static org.junit.Assert.*;

public class FreesoundTest
{
	String clientId, token;
	Freesound freesound;
	
	@Before
	public void setup()
	{
		// Read the configuration
		Reader reader = new InputStreamReader(getClass().getResourceAsStream("/config.json"));
		JsonObject config = new JsonParser().parse(reader).getAsJsonObject();
		clientId = config.get("id").getAsString();
		token = config.get("token").getAsString();
		
		// Build the test object
		freesound = Freesound.builder().withToken(token).build();
	}
	
	@Test
	public void simpleRequestTest()
	{
		SimpleRequest request = new SimpleRequest("sounds", 1234);
		System.out.println(request.httpRequest().getURI());
		JsonObject response = freesound.request(request).safeAwait();
		
		assertNotNull(response);
		assertEquals(1234,                   response.get("id").getAsInt());
		assertEquals("180404D.mp3",          response.get("name").getAsString());
		assertEquals("Traveling drum sound", response.get("description").getAsString());
	}
	
	@Test
	public void soundRequestTest()
	{
		Sound sound = freesound.request(new SoundRequest(1234)).safeAwait();
		assertEquals(1234, sound.id());
		assertEquals("180404D.mp3", sound.name());
		
		// Logging some stuff
		System.out.println(sound.geotag());
		System.out.println(sound.previewUrl(Sound.Preview.HIGH_QUALITY_MP3));
		System.out.println(sound.imageUrl(Sound.Image.WAVEFORM_LARGE));
	}
	
	@Test
	public void similarSoundsRequestTest()
	{
		Sound[] response = freesound.request(new SimilarSounds(1234)).safeAwait();
		System.out.println(Arrays.toString(response));
	}
	
	@Test
	public void textSearchTest()
	{
		TextSearchQuery query = new TextSearchQuery()
				.include("foo")
				.include("bar")
				.include("abc")
				.exclude("abc");
		assertEquals("+\"foo\" +\"bar\" -\"abc\"", query.toString());
		
		TextSearch request = new TextSearch(query)
				.includeField("id")
				.includeField("url");
		
		Sound[] response = freesound.request(request).safeAwait();
		System.out.println(response[0].url());
		
		assertEquals(34123, response[0].id());
	}
}
