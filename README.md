# JFreesound
*Freesound API client library for Java.*

### Download
Until **JFreesound** is released, you will have to download the source code
(or clone the GitHub repository) and build it yourself with the libraries listed
[below](#libraries). The library should be released when the [Basic Functionality](https://github.com/michalwa/JFreesound/milestone/1)
milestone is complete.

### Usage
Before implementing this library, it is recommended to review
the original [API documentation](https://freesound.org/docs/api/).

As a demonstration, try playing with this code:
```java
import pl.michalwa.jfreesound.Freesound;
import pl.michalwa.jfreesound.request.SoundRequest;
import pl.michalwa.jfreesound.data.Sound;

public class JFreesoundDemo
{
    public static void main(String[] args)
    {
        Freesound freesound = Freesound.builder().withToken("YOUR_API_TOKEN").build();
        Sound sound = freesound.request(new SoundRequest(1234)).safeAwait();
    }
}
```
In the future, more examples will be available in the GitHub repository
[wiki](https://github.com/michalwa/JFreesound/wiki).

### Build
In order to contribute to the project you'll need to clone
it from the github repository and build it and test it manually.
For testing you'll need to include a resource directory in
your builds with a `config.json` file within it. The config file
contains API credentials used to access the Freesound API for testing,
that you'll need to acquire for yourself. The file must follow the
following format:
```json
{
    "id": "YOUR_CLIENT_ID",
    "token": "YOUR_API_TOKEN"
}
```
Unfortunately, testing OAuth2 authentication requires acquiring the authorization code
manually and then storing these properties in the config file:
```json
{
    "tempAuthCode": "TEMPORARY_AUTHORIZATION_CODE",
    "tempRefreshToken": "TEMPORARY_REFRESH_TOKEN",
    "tempAccessToken": "TEMPORARY_ACCESS_TOKEN"
}
```
This configuration file's purpose is just for testing and is not required
when implementing **JFreesound** in an application. In your project,
the token and other data can be provided to the library directly *(see [usage](#usage))*.

### Libraries
**JFreesound** uses the following open-source libraries:
  + [Gson v2.8.4](https://github.com/google/gson) licensed under [Apache License 2.0](https://github.com/google/gson/blob/master/LICENSE) - [*(download)*](http://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.4/)

Common HTTP client implementation:
  + [Apache HttpClient v4.5.5](https://hc.apache.org/httpcomponents-client-4.5.x/) licensed under [Apache License 2.0](http://www.apache.org/licenses/) - [*(download)*](https://hc.apache.org/downloads.cgi)  
    See: [ApacheHttpClient.java](/test/ApacheHttpClient.java)
