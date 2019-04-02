import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.Before;
import org.junit.Test;
import pl.michalwa.jfreesound.Freesound;
import pl.michalwa.jfreesound.auth.OAuth2;
import pl.michalwa.jfreesound.request.SimpleRequest;

@SuppressWarnings("unused")
public class OAuth2Test
{
	private String clientId, token, authCode, refreshToken, accessToken;
	private Freesound freesound;
	
	@Before
	public void setup()
	{
		// Setup the HTTP client
		Freesound.setHttpClient(new ApacheHttpClient());
		
		// Read the configuration
		Reader reader = new InputStreamReader(getClass().getResourceAsStream("/config.json"));
		JsonObject config = new JsonParser().parse(reader).getAsJsonObject();
		clientId     = config.get("id").getAsString();
		token        = config.get("token").getAsString();
		authCode     = config.get("tempAuthCode").getAsString();
		refreshToken = config.get("tempRefreshToken").getAsString();
		accessToken  = config.get("tempAccessToken").getAsString();
	}
	
	@Test
	public void authenticationTest()
	{
		/*
		OAuth2 auth = OAuth2.request()
		                    .withCredentials(clientId, token)
		                    .withAuthCode(authCode)
		                    .submit()
		                    .safeAwait();
		*/
		
		OAuth2 auth = new OAuth2(accessToken);
		
		System.out.println("Access token: " + auth.accessToken());
		System.out.println("Refresh token: " + auth.refreshToken());
		System.out.println("Expires in: " + auth.expiresIn());
		
		freesound = Freesound.builder()
				.withAuthentication(auth)
				.build();
		
		System.out.println(freesound.request(new SimpleRequest("me")).safeAwait().toString());
	}
}
