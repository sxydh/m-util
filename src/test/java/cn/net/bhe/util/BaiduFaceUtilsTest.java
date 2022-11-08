package cn.net.bhe.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

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

    @Test
    void addGroup() {
        try {
            String ret = baiduFaceUtils.addGroup("group02");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void listGroup() {
        try {
            String ret = baiduFaceUtils.listGroup("0", "10");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteGroup() {
        try {
            String ret = baiduFaceUtils.deleteGroup("group_test1");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void listUser() {
        try {
            String ret = baiduFaceUtils.listUser(
                    "group01",
                    "0",
                    "10");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getUser() {
        try {
            String ret = baiduFaceUtils.getUser(
                    "group01",
                    "user01");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteUser() {
        try {
            String ret = baiduFaceUtils.deleteUser(
                    "group01",
                    "user01");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addFace() {
        try {
            String ret = baiduFaceUtils.addFace(
                    IOUtils.resourceToString("/BaiduFaceUtilsTest_face01.txt", StandardCharsets.UTF_8),
                    "group01",
                    "user01",
                    JSON.toJSONString(CollUtils.map(
                            "age", "18",
                            "gender", "male",
                            "job", "boss")));
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void listFace() {
        try {
            String ret = baiduFaceUtils.listFace(
                    "group01",
                    "user01");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteFace() {
        try {
            String ret = baiduFaceUtils.deleteFace(
                    UUID.randomUUID().toString(),
                    "group01",
                    "user01",
                    "291524638c0280320f558b83bf885336");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void searchFace() {
        try {
            String ret = baiduFaceUtils.searchFace(
                    IOUtils.resourceToString("/BaiduFaceUtilsTest_face01.txt", StandardCharsets.UTF_8),
                    "group01",
                    "1");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}