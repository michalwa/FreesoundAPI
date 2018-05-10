import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.Before;
import org.junit.Test;
import pl.michalwa.jfreesound.Freesound;
import pl.michalwa.jfreesound.data.Sound;
import pl.michalwa.jfreesound.request.SoundRequest;

import static org.junit.Assert.assertEquals;

public class SoundRequestTest
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
	public void soundRequestTest()
	{
		Sound sound = freesound.request(new SoundRequest(1234)).awaitAndCatch();
		assertEquals(1234, sound.id());
		assertEquals("180404D.mp3", sound.name());
		
		// Logging some stuff
		System.out.println(sound.geotag());
		System.out.println(sound.previewUrl(Sound.Preview.HQ_MP3));
		System.out.println(sound.imageUrl(Sound.Image.WAVEFORM_L));
	}
}
