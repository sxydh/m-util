package cn.net.bhe.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
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
public class WeChatUtils {

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

    private static String get(URI uri) throws Exception {
        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(uri);
            return getHttpResponse(closeableHttpClient, httpGet);
        }
    }

    private static String post(URI uri, Map<String, ?> body) throws Exception {
        return post(uri, JSON.toJSONString(body));
    }

    private static String post(URI uri, String body) throws Exception {
        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);
            StringEntity stringEntity = new StringEntity(body, StandardCharsets.UTF_8);
            httpPost.setEntity(stringEntity);
            return getHttpResponse(closeableHttpClient, httpPost);
        }
    }

    /**
     * ????????????????????????access_token.
     *
     * @param appid  ?????????????????????
     * @param secret ???????????????????????????
     * @return ??????
     * @throws Exception
     */
    public static String getAccessToken(String appid, String secret) throws Exception {
        URI uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/token")
                .setParameter("grant_type", "client_credential")
                .setParameter("appid", appid)
                .setParameter("secret", secret)
                .build();
        return get(uri);
    }

    /**
     * ??????????????????
     *
     * @param appid  ????????? appId
     * @param secret ????????? appSecret
     * @param jsCode ?????????????????? code
     * @return ??????
     * @throws Exception
     */
    public static String jscode2session(String appid, String secret, String jsCode) throws Exception {
        URI uri = new URIBuilder("https://api.weixin.qq.com/sns/jscode2session")
                .setParameter("grant_type", "authorization_code")
                .setParameter("appid", appid)
                .setParameter("secret", secret)
                .setParameter("js_code", jsCode)
                .build();
        return get(uri);
    }

    public static String sendSubscribedMessage(String accessToken, SubscribedMessage subscribedMessage) throws Exception {
        URI uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/message/subscribe/send")
                .setParameter("access_token", accessToken)
                .build();
        // ???????????????, config??????singleton??????, ??????????????????????????????.
        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        return post(uri, JSONObject.toJSONString(subscribedMessage, config));
    }

}
