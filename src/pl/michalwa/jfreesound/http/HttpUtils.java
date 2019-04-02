package pl.michalwa.jfreesound.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Contains static utility methods for working with HTTP stuff
 */
public class HttpUtils
{
	/**
	 * Encodes the given parameters into a query-string form.
	 *
	 * @param params the parameters to encode
	 * @param encoding the encoding to use
	 *
	 * @return the generated string
	 */
	public static String encodeParams(Map<String, String> params, String encoding)
	{
		List<String> encodedParams = params.entrySet().stream()
			.map(param -> {
				try {
					return URLEncoder.encode(param.getKey(), encoding) + "=" + URLEncoder.encode(param.getValue(), "UTF-8");
				} catch(UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return null;
			})
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
		
		return String.join("&", encodedParams);
	}
}
