package cn.net.bhe.util;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class BaiduFaceUtilsTest {

    static final BaiduFaceUtils baiduFaceUtils = new BaiduFaceUtils("", "");

    @Test
    void getToken() {
        try {
            String ret = baiduFaceUtils.getToken();
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void detectFace() {
        try {
            String ret = baiduFaceUtils.detectFace(IOUtils.resourceToString("/BaiduFaceUtilsTest_face01.txt", StandardCharsets.UTF_8));
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}