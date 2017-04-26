package utils;

import lombok.extern.log4j.Log4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Admin on 23.04.2017.
 */
@Log4j
public class RequestUtils {

    public static String sendGetRequestToUrl(String url) {
        try ( CloseableHttpClient httpClient = HttpClientBuilder.create().build() ) {
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            HttpResponse result = httpClient.execute(request);

            return EntityUtils.toString(result.getEntity(), "UTF-8");
        } catch ( IOException  e) {
            log.error("Got error while sending request to url " + url + " e:" + e);
        }
        return "";
    }

}
