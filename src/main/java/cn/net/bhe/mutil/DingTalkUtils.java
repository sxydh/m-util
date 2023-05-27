package cn.net.bhe.mutil;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.Random;

/**
 * @author Administrator
 * @see <a href="https://open-dev.dingtalk.com/apiExplorer?spm=ding_open_doc.document.0.0.285976e0SHY0a5#/?devType=org&api=oauth2_1.0%23CreateJsapiTicket">调试服务端API</a>
 */
public class DingTalkUtils {

    /**
     * 获取企业内部应用的access_token.
     *
     * @param appKey    应用的唯一标识key
     * @param appSecret 应用的密钥
     * @return 结果
     * @throws Exception
     */
    public static String getAccessToken(String appKey, String appSecret) throws Exception {
        URI uri = new URIBuilder("https://oapi.dingtalk.com/gettoken")
                .setParameter("appkey", appKey)
                .setParameter("appsecret", appSecret)
                .build();
        return HttpClientUtils.get(uri);
    }

    /**
     * 通过免登码获取用户信息.
     *
     * @param accessToken 调用服务端API的应用凭证
     * @param code        免登授权码
     * @return 结果
     * @throws Exception
     */
    public static String getUserInfo(String accessToken, String code) throws Exception {
        URI uri = new URIBuilder("https://oapi.dingtalk.com/user/getuserinfo")
                .setParameter("access_token", accessToken)
                .setParameter("code", code)
                .build();
        return HttpClientUtils.get(uri);
    }

    /**
     * 根据手机号获取userid.
     * <br/>
     * 需要开通<a href="https://open-dev.dingtalk.com/fe/app?spm=a2q3p.21071111.0.0.681a65ee6wXaMD#/appMgr/inner/h5/1106618607/4">根据手机号姓名获取成员信息的接口访问权限</a>.
     *
     * @param accessToken 调用服务端API授权凭证
     * @param mobile      要获取的用户手机号
     * @return 结果
     * @throws Exception
     */
    public static String getUserIdByMobile(String accessToken, String mobile) throws Exception {
        URI uri = new URIBuilder("https://oapi.dingtalk.com/user/get_by_mobile")
                .setParameter("access_token", accessToken)
                .setParameter("mobile", mobile)
                .build();
        return HttpClientUtils.get(uri);
    }

    /**
     * 根据userid获取用户详情.
     * <br/>
     * 需要开通<a href="https://open-dev.dingtalk.com/fe/app?spm=a2q3p.21071111.0.0.681a65ee6wXaMD#/appMgr/inner/h5/1106618607/4">成员信息读权限</a>.
     *
     * @param accessToken 调用服务端API的应用凭证
     * @param userId      员工唯一标识userid
     * @return 结果
     * @throws Exception
     */
    public static String getUserDetail(String accessToken, String userId) throws Exception {
        URI uri = new URIBuilder("https://oapi.dingtalk.com/user/get")
                .setParameter("access_token", accessToken)
                .setParameter("userid", userId)
                .build();
        return HttpClientUtils.get(uri);
    }

    /**
     * 发送工作通知.
     *
     * @param accessToken 调用服务端API的应用凭证
     * @param body        详见{@link CorpConversationMessage}
     * @return 结果
     * @throws Exception
     */
    public static String sendCorpConversationMessage(String accessToken, CorpConversationMessage body) throws Exception {
        URI uri = new URIBuilder("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2")
                .setParameter("access_token", accessToken)
                .build();
        // 生产环境中, config要做singleton处理, 要不然会存在性能问题.
        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        return HttpClientUtils.post(uri, JSONObject.toJSONString(body, config));
    }

    /**
     * 获取工作通知消息的发送进度.
     *
     * @param accessToken 调用服务端API的应用凭证
     * @param agentId     发送消息时使用的微应用的ID
     * @param taskId      发送消息时钉钉返回的任务ID
     * @return 结果
     * @throws Exception
     */
    public static String getProgressOfSendingCorpConversationMessage(String accessToken, String agentId, String taskId) throws Exception {
        URI uri = new URIBuilder("https://oapi.dingtalk.com/topapi/message/corpconversation/getsendprogress")
                .setParameter("access_token", accessToken)
                .build();
        return HttpClientUtils.post(uri, CollUtils.map(
                "agent_id", agentId,
                "task_id", taskId
        ));
    }

    /**
     * 撤回工作通知消息.
     *
     * @param accessToken 调用服务端API的应用凭证
     * @param agentId     发送消息时使用的微应用的ID
     * @param taskId      发送消息时钉钉返回的任务ID
     * @return 结果
     * @throws Exception
     */
    public static String recallCorpConversationMessage(String accessToken, String agentId, String taskId) throws Exception {
        URI uri = new URIBuilder("https://oapi.dingtalk.com/topapi/message/corpconversation/recall")
                .setParameter("access_token", accessToken)
                .build();
        return HttpClientUtils.post(uri, CollUtils.map(
                "agent_id", agentId,
                "msg_task_id", taskId
        ));
    }

    /**
     * 获取接口调用票据.
     *
     * @param accessToken accessToken
     * @return 结果
     * @throws Exception
     */
    public static String getJsapiTicket(String accessToken) throws Exception {
        URI uri = new URIBuilder("https://oapi.dingtalk.com/get_jsapi_ticket")
                .setParameter("access_token", accessToken)
                .build();
        return HttpClientUtils.get(uri);
    }

    /**
     * 获取随机字符串.
     *
     * @param count 长度
     * @return 结果
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
     * 计算签名.
     *
     * @param jsticket  接口调用票据
     * @param nonceStr  自定义固定字符串
     * @param timeStamp 时间戳
     * @param url       当前网页的URL, 不包含#及其后面部分
     * @return 结果
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
