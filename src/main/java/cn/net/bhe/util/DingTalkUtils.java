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
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * @author Administrator
 */
public class DingTalkUtils {

    private static String get(URI uri) throws Exception {
        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(uri);
            return getHttpResponse(closeableHttpClient, httpGet);
        }
    }

    private static String post(URI uri, Map<String, Object> body) throws Exception {
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

    /**
     * ???????????????????????????access_token.
     *
     * @param appKey    ?????????????????????key
     * @param appSecret ???????????????
     * @return ??????
     * @throws Exception
     */
    public static String getAccessToken(String appKey, String appSecret) throws Exception {
        URI uri = new URIBuilder("https://oapi.dingtalk.com/gettoken")
                .setParameter("appkey", appKey)
                .setParameter("appsecret", appSecret)
                .build();
        return get(uri);
    }

    /**
     * ?????????????????????????????????.
     *
     * @param accessToken ???????????????API???????????????
     * @param code        ???????????????
     * @return ??????
     * @throws Exception
     */
    public static String getUserInfo(String accessToken, String code) throws Exception {
        URI uri = new URIBuilder("https://oapi.dingtalk.com/user/getuserinfo")
                .setParameter("access_token", accessToken)
                .setParameter("code", code)
                .build();
        return get(uri);
    }

    /**
     * ?????????????????????userid.
     * <br/>
     * ????????????<a href="https://open-dev.dingtalk.com/fe/app?spm=a2q3p.21071111.0.0.681a65ee6wXaMD#/appMgr/inner/h5/1106618607/4">????????????????????????????????????????????????????????????</a>.
     *
     * @param accessToken ???????????????API????????????
     * @param mobile      ???????????????????????????
     * @return ??????
     * @throws Exception
     */
    public static String getUserIdByMobile(String accessToken, String mobile) throws Exception {
        URI uri = new URIBuilder("https://oapi.dingtalk.com/user/get_by_mobile")
                .setParameter("access_token", accessToken)
                .setParameter("mobile", mobile)
                .build();
        return get(uri);
    }

    /**
     * ??????userid??????????????????.
     * <br/>
     * ????????????<a href="https://open-dev.dingtalk.com/fe/app?spm=a2q3p.21071111.0.0.681a65ee6wXaMD#/appMgr/inner/h5/1106618607/4">?????????????????????</a>.
     *
     * @param accessToken ???????????????API???????????????
     * @param userId      ??????????????????userid
     * @return ??????
     * @throws Exception
     */
    public static String getUserDetail(String accessToken, String userId) throws Exception {
        URI uri = new URIBuilder("https://oapi.dingtalk.com/user/get")
                .setParameter("access_token", accessToken)
                .setParameter("userid", userId)
                .build();
        return get(uri);
    }

    /**
     * ??????????????????.
     *
     * @param accessToken ???????????????API???????????????
     * @param body        ??????{@link CorpConversationMessage}
     * @return ??????
     * @throws Exception
     */
    public static String sendCorpConversationMessage(String accessToken, CorpConversationMessage body) throws Exception {
        URI uri = new URIBuilder("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2")
                .setParameter("access_token", accessToken)
                .build();
        // ???????????????, config??????singleton??????, ??????????????????????????????.
        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        return post(uri, JSONObject.toJSONString(body, config));
    }

    /**
     * ???????????????????????????????????????.
     *
     * @param accessToken ???????????????API???????????????
     * @param agentId     ????????????????????????????????????ID
     * @param taskId      ????????????????????????????????????ID
     * @return ??????
     * @throws Exception
     */
    public static String getProgressOfSendingCorpConversationMessage(String accessToken, String agentId, String taskId) throws Exception {
        URI uri = new URIBuilder("https://oapi.dingtalk.com/topapi/message/corpconversation/getsendprogress")
                .setParameter("access_token", accessToken)
                .build();
        return post(uri, Map.of(
                "agent_id", agentId,
                "task_id", taskId
        ));
    }

    /**
     * ????????????????????????.
     *
     * @param accessToken ???????????????API???????????????
     * @param agentId     ????????????????????????????????????ID
     * @param taskId      ????????????????????????????????????ID
     * @return ??????
     * @throws Exception
     */
    public static String recallCorpConversationMessage(String accessToken, String agentId, String taskId) throws Exception {
        URI uri = new URIBuilder("https://oapi.dingtalk.com/topapi/message/corpconversation/recall")
                .setParameter("access_token", accessToken)
                .build();
        return post(uri, Map.of(
                "agent_id", agentId,
                "msg_task_id", taskId
        ));
    }

    /**
     * ????????????????????????.
     *
     * @param accessToken accessToken
     * @return ??????
     * @throws Exception
     */
    public static String getJsapiTicket(String accessToken) throws Exception {
        URI uri = new URIBuilder("https://oapi.dingtalk.com/get_jsapi_ticket")
                .setParameter("access_token", accessToken)
                .build();
        return get(uri);
    }

    /**
     * ?????????????????????.
     *
     * @param count ??????
     * @return ??????
     */
    public static String getRandomStr(int count) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * ????????????.
     *
     * @param jsticket  ??????????????????
     * @param nonceStr  ????????????????????????
     * @param timeStamp ?????????
     * @param url       ???????????????URL, ?????????#??????????????????
     * @return ??????
     * @throws Exception
     */
    public static String sign(String jsticket, String nonceStr, long timeStamp, String url) throws Exception {
        String plain = "jsapi_ticket=" + jsticket + "&noncestr=" + nonceStr + "&timestamp=" + timeStamp
                + "&url=" + decodeUrl(url);
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-256");
            sha1.reset();
            sha1.update(plain.getBytes(StandardCharsets.UTF_8));
            return byteToHex(sha1.digest());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String decodeUrl(String url) throws Exception {
        URL urler = new URL(url);
        StringBuilder urlBuffer = new StringBuilder();
        urlBuffer.append(urler.getProtocol());
        urlBuffer.append(":");
        if (urler.getAuthority() != null && urler.getAuthority().length() > 0) {
            urlBuffer.append("//");
            urlBuffer.append(urler.getAuthority());
        }
        if (urler.getPath() != null) {
            urlBuffer.append(urler.getPath());
        }
        if (urler.getQuery() != null) {
            urlBuffer.append('?');
            urlBuffer.append(URLDecoder.decode(urler.getQuery(), StandardCharsets.UTF_8));
        }
        return urlBuffer.toString();
    }

}
