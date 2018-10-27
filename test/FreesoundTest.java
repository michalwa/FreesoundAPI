import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import pl.michalwa.jfreesound.Freesound;
import pl.michalwa.jfreesound.data.Pack;
import pl.michalwa.jfreesound.data.Sound;
import pl.michalwa.jfreesound.data.User;
import pl.michalwa.jfreesound.request.*;
import pl.michalwa.jfreesound.request.search.TextSearch;
import pl.michalwa.jfreesound.request.search.TextSearchQuery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FreesoundTest
{
	private Freesound freesound;
	
	@Before
	public void setup()
	{
		// Read the configuration
		Reader reader = new InputStreamReader(getClass().getResourceAsStream("/config.json"));
		JsonObject config = new JsonParser().parse(reader).getAsJsonObject();
		String token = config.get("token").getAsString();
		
		// Build the test object
		freesound = Freesound.builder().withToken(token).build();
	}
	
	@Test
	public void simpleRequest()
	{
		SimpleRequest request = new SimpleRequest("sounds", 1234);
		JsonObject response = freesound.request(request).safeAwait();
		
		assertNotNull(response);
		assertEquals(1234,                   response.get("id").getAsInt());
		assertEquals("180404D.mp3",          response.get("name").getAsString());
		assertEquals("Traveling drum sound", response.get("description").getAsString());
	}
	
	@Test
	public void soundInstance()
	{
		Sound sound = freesound.request(new SoundRequest(81189)).safeAwait();
		assertEquals(81189, sound.id());
		assertEquals("Brunswiek.wav", sound.name());
		
		System.out.println(sound.geotag());
		System.out.println(sound.previewUrl(Sound.Preview.HIGH_QUALITY_MP3));
		System.out.println(sound.imageUrl(Sound.Image.WAVEFORM_LARGE));
	}
	
	@Test
	public void similarSounds()
	{
		Sound[] response = freesound.request(new SimilarSounds(1234)).safeAwait();
		System.out.println(Arrays.toString(response));
	}
	
	@Test
	public void textSearch()
	{
		TextSearchQuery query = new TextSearchQuery()
				.include("foo")
				.include("bar")
				.include("abc")
				.exclude("abc");
		assertEquals("+\"foo\" +\"bar\" -\"abc\"", query.toString());
		
		TextSearch request = new TextSearch(query);
		request.includeFields("id", "url");
		
		Sound[] response = freesound.request(request).safeAwait();
		System.out.println(response[0].url());
		
		assertEquals(34123, response[0].id());
	}
	
	@Test
	public void userInstance()
	{
		User user = freesound.user("michalwa2003").safeAwait();
		assertEquals("michalwa2003", user.username());
		assertEquals(LocalDateTime.parse("2015-05-07T19:09:17.983879"), user.dateJoined());
		
		System.out.println(user);
	}
	
	@Test
	public void packInstance()
	{
		Pack pack = freesound.pack(9678).safeAwait();
		
		assertEquals(9678, pack.id());
		assertEquals("Zoo", pack.name());
		
		System.out.println(pack);
	}
}
