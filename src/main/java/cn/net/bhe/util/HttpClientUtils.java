package cn.net.bhe.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @author Administrator
 */
public class HttpClientUtils {

    public static String get(URI uri) throws Exception {
        return get(uri, null);
    }

    public static String get(URI uri, Header[] headers) throws Exception {
        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(uri);
            if (ObjectUtils.isNotEmpty(headers)) {
                httpGet.setHeaders(headers);
            }
            return getHttpResponse(closeableHttpClient, httpGet);
        }
    }

    public static String post(URI uri, Map<String, Object> body) throws Exception {
        return post(uri, null, body);
    }

    public static String post(URI uri, Header[] headers, Map<String, Object> body) throws Exception {
        return post(uri, headers, JSON.toJSONString(body));
    }

    public static String post(URI uri, String body) throws Exception {
        return post(uri, null, body);
    }

    public static String post(URI uri, Header[] headers, String body) throws Exception {
        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);
            if (ObjectUtils.isNotEmpty(headers)) {
                httpPost.setHeaders(headers);
            }
            StringEntity stringEntity = new StringEntity(body, StandardCharsets.UTF_8);
            httpPost.setEntity(stringEntity);
            return getHttpResponse(closeableHttpClient, httpPost);
        }
    }

    private static String getHttpResponse(CloseableHttpClient closeableHttpClient, HttpUriRequest request) throws Exception {
        try (CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(request)) {
            String resStr = EntityUtils.toString(closeableHttpResponse.getEntity(), StandardCharsets.UTF_8).trim();
            int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK != statusCode) {
                throw new HttpException(Objects.toString(resStr, String.valueOf(statusCode)));
            }
            return resStr;
        }
    }

}
