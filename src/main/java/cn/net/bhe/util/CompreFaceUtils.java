package cn.net.bhe.util;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.net.URI;
import java.util.Map;

/**
 * @author Administrator
 * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md">Rest API description</a>
 */
public class CompreFaceUtils {

    private static final String CF_FD = "http://172.18.5.138:8000/api/v1/detection/detect";
    private static final String CF_FV = "http://172.18.5.138:8000/api/v1/verification/verify";

    private static Header[] buildHeaders(String xApiKey) {
        return new Header[]{
                new BasicHeader("Content-Type", "application/json"),
                new BasicHeader("x-api-key", xApiKey)};
    }

    /**
     * Face Detection Service
     *
     * @param xApiKey api key of the Face Detection service, created by the user.
     * @param file    image base64
     * @return result
     * @throws Exception HTTP Exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#face-detection-service">Face Detection Service</a>
     */
    public static String faceDetect(String xApiKey, String file) throws Exception {
        return HttpClientUtils.post(
                new URI(CF_FD),
                buildHeaders(xApiKey),
                Map.of("file", file));
    }

    /**
     * Face Verification Service
     *
     * @param xApiKey     api key of the Face Detection service, created by the user.
     * @param sourceImage sourceImage base64
     * @param targetImage targetImage base64
     * @return result
     * @throws Exception HTTP Exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#face-verification-service">Face Verification Service</a>
     */
    public static String faceVerify(String xApiKey, String sourceImage, String targetImage) throws Exception {
        return HttpClientUtils.post(
                new URI(CF_FV),
                buildHeaders(xApiKey),
                Map.of("source_image", sourceImage,
                        "target_image", targetImage));
    }

}
