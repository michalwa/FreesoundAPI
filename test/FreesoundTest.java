import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import pl.michalwa.jfreesound.Freesound;
import pl.michalwa.jfreesound.data.Sound;
import pl.michalwa.jfreesound.request.SimilarSoundsRequest;
import pl.michalwa.jfreesound.request.SimpleRequest;
import pl.michalwa.jfreesound.request.SoundListRequest;

import static org.junit.Assert.*;

public class FreesoundTest
{
	String clientId, token;
	Freesound freesound;
	volatile boolean recievedAsync = false;
	
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
		JsonObject response = freesound.request(new SimpleRequest("sounds", 1234)).safeAwait();
		
		assertNotNull(response);
		assertEquals(1234,                   response.get("id").getAsInt());
		assertEquals("180404D.mp3",          response.get("name").getAsString());
		assertEquals("Traveling drum sound", response.get("description").getAsString());
	}
}
