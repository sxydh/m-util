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

    /**
     * 获取接口调用凭证access_token.
     *
     * @param appid  小程序唯一凭证
     * @param secret 小程序唯一凭证密钥
     * @return 结果
     * @throws Exception
     */
    public static String getAccessToken(String appid, String secret) throws Exception {
        URI uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/token")
                .setParameter("grant_type", "client_credential")
                .setParameter("appid", appid)
                .setParameter("secret", secret)
                .build();
        return HttpClientUtils.get(uri);
    }

    /**
     * 登录凭证校验
     *
     * @param appid  小程序 appId
     * @param secret 小程序 appSecret
     * @param jsCode 登录时获取的 code
     * @return 结果
     * @throws Exception
     */
    public static String jscode2session(String appid, String secret, String jsCode) throws Exception {
        URI uri = new URIBuilder("https://api.weixin.qq.com/sns/jscode2session")
                .setParameter("grant_type", "authorization_code")
                .setParameter("appid", appid)
                .setParameter("secret", secret)
                .setParameter("js_code", jsCode)
                .build();
        return HttpClientUtils.get(uri);
    }

    public static String sendSubscribedMessage(String accessToken, SubscribedMessage subscribedMessage) throws Exception {
        URI uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/message/subscribe/send")
                .setParameter("access_token", accessToken)
                .build();
        // 生产环境中, config要做singleton处理, 要不然会存在性能问题.
        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        return HttpClientUtils.post(uri, JSONObject.toJSONString(subscribedMessage, config));
    }

}
