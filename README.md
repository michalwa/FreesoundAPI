**JFreesound** is a Freesound API client library for Java, written in Java.

### Usage
As a demonstration, try playing with this code:
```java
Freesound freesound = Freesound.builder().withToken("YOUR_API_TOKEN").build();
JsonObject sound = freesound.request(new SimpleRequest("sounds", 1234), null);
```
In the future, more examples will be available in the GitHub repository wiki.

### Build
In order to contribute to the project you'll need to clone
it from the github repository and build it and test it manually.
For testing you'll need to include a resource directory in
your builds with a `config.json` file within it. The config file
contains the API token used to access the Freesound API for testing,
that you'll need to acquire for yourself. The file follows the following
format:
```json
{
    "token": "YOUR_API_TOKEN"
}
```

### Libraries
**JFreesound** uses the following open-source libraries:
  + [Apache HttpClient v4.5.5](https://hc.apache.org/httpcomponents-client-4.5.x/) licensed under [Apache License 2.0](http://www.apache.org/licenses/)
  + [Gson v2.8.4](https://github.com/google/gson) licensed under [Apache License 2.0](https://github.com/google/gson/blob/master/LICENSE)
