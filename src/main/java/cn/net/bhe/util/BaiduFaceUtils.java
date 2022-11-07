package cn.net.bhe.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.Header;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.Asserts;

import java.net.URI;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Administrator
 * @see <a href="https://cloud.baidu.com/apiexplorer/index.html?Product=GWSE-nmhroEsyriA&Api=GWAI-w2sr6pvyXzJ">人脸识别</a>
 */
public class BaiduFaceUtils {

    /**
     * 服务器地址
     */
    private final String server = "https://aip.baidubce.com/";
    private final String codeAccessTokenExpired = "110";
    /**
     * 应用的API Key
     */
    private final String clientId;
    /**
     * 应用的Secret Key
     */
    private final String clientSecret;
    private String accessToken;

    private final String hdnCt = "Content-Type";
    private final String hdvAj = "application/json";

    /**
     * 获取AccessToken
     */
    private final String apiToken = "/oauth/2.0/token";
    /**
     * 人脸检测
     */
    private final String apiDetect = "/rest/2.0/face/v3/detect";

    private BaiduFaceUtils(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public static BaiduFaceUtils init(String clientId, String clientSecret) {
        return new BaiduFaceUtils(clientId, clientSecret);
    }

    private String get(Supplier<URI> uriSupplier, Supplier<Header[]> headersSupplier) throws Exception {
        String ret = HttpClientUtils.get(uriSupplier.get(), headersSupplier.get());
        JSONObject retJson = JSON.parseObject(ret);
        String errorCode = retJson.getString("error_code");
        // accessToken检查
        if (codeAccessTokenExpired.equals(errorCode)) {
            accessToken = getToken();
            return HttpClientUtils.get(uriSupplier.get(), headersSupplier.get());
        }
        return ret;
    }

    private String post(Supplier<URI> uriSupplier, Supplier<Header[]> headersSupplier, Supplier<Map<String, Object>> bodySupplier) throws Exception {
        String ret = HttpClientUtils.post(uriSupplier.get(), headersSupplier.get(), bodySupplier.get());
        JSONObject retJson = JSON.parseObject(ret);
        String errorCode = retJson.getString("error_code");
        // accessToken检查
        if (codeAccessTokenExpired.equals(errorCode)) {
            ret = getToken();
            retJson = JSON.parseObject(ret);
            accessToken = retJson.getString("access_token");
            Asserts.check(ObjectUtils.isNotEmpty(accessToken), "获取accessToken失败！");
            return HttpClientUtils.post(uriSupplier.get(), headersSupplier.get(), bodySupplier.get());
        }
        return ret;
    }

    /**
     * 获取AccessToken
     *
     * @return result
     * @throws Exception exception
     * @see <a href="https://cloud.baidu.com/apiexplorer/index.html?Product=GWSE-nmhroEsyriA&Api=GWAI-ZrbC4DkR2rP">获取AccessToken</a>
     */
    public String getToken() throws Exception {
        return HttpClientUtils.get(new URIBuilder(server + apiToken)
                        .addParameter("grant_type", "client_credentials")
                        .addParameter("client_id", clientId)
                        .addParameter("client_secret", clientSecret)
                        .build(),
                null);
    }

    public String detectFace(String image) throws Exception {
        return post(() -> HttpClientUtils.uri(server + apiDetect,
                        "access_token", accessToken),
                () -> HttpClientUtils.buildHeaders(hdnCt, hdvAj),
                () -> Map.of(
                        "image", image,
                        "image_type", "BASE64",
                        "face_field", "age,beauty,expression,face_shape,gender,glasses,landmark,landmark150,quality,eye_status,emotion,face_type,mask,spoofing",
                        "face_type", "LIVE",
                        "liveness_control", "NORMAL"));
    }

}
