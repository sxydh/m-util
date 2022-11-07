package cn.net.bhe.util;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class BaiduFaceUtilsTest {

    static final String clientId = "";
    static final String clientSecret = "";

    @Test
    void getToken() {
        try {
            BaiduFaceUtils baiduFaceUtils = BaiduFaceUtils.init(clientId, clientSecret);
            String ret = baiduFaceUtils.getToken();
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void detectFace() {
        try {
            BaiduFaceUtils baiduFaceUtils = BaiduFaceUtils.init(clientId, clientSecret);
            String ret = baiduFaceUtils.detectFace(IOUtils.resourceToString("/BaiduFaceUtilsTest_face01.txt", StandardCharsets.UTF_8));
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}