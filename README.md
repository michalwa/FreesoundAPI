**JFreesound** is a Freesound API client library for Java, written in Java.

### Download
Until **JFreesound** is released, you will have to download the source code
(or clone the GitHub repository) and build it yourself with the libraries listed
[below](#libraries).

### Usage
As a demonstration, try playing with this code:
```java
import com.google.gson.JsonObject;
import pl.michalwa.jfreesound.Freesound;
import pl.michalwa.jfreesound.request.SimpleAPIRequest;

public class JFreesoundDemo
{
    public static void main(String[] args)
    {
        Freesound freesound = Freesound.builder().withToken("YOUR_API_TOKEN").build();
        JsonObject sound = freesound.request(new SimpleAPIRequest("sounds", 1234), null);
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
contains the API token (aka *client secret*) used to access the Freesound
API for testing, that you'll need to acquire for yourself. The file
must follow the following format:
```json
{
    "id": "YOUR_CLIENT_ID",
    "token": "YOUR_API_TOKEN"
}
```
This configuration file's purpose is just for testing and is not required
when implementing **JFreesound** in an application. In your project,
the token and other data can be provided to the library directly *(see [usage](#usage))*.

### Libraries
**JFreesound** uses the following open-source libraries:
  + [Apache HttpClient v4.5.5](https://hc.apache.org/httpcomponents-client-4.5.x/) licensed under [Apache License 2.0](http://www.apache.org/licenses/)
  + [Gson v2.8.4](https://github.com/google/gson) licensed under [Apache License 2.0](https://github.com/google/gson/blob/master/LICENSE)
