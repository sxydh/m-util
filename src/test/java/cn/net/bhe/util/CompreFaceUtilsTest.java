package cn.net.bhe.util;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;

class CompreFaceUtilsTest {

    static final CompreFaceUtils compreFaceUtils = new CompreFaceUtils(
            "http://172.18.5.138:8000/",
            "eda569dc-64ef-4338-a1a3-7c633f5f928c",
            "37240f60-4d10-4db3-89d2-fcccd8c1d6c2",
            "74b97ce2-031b-4062-a591-9929df7a0f9a");

    @Test
    void addSubject() {
        try {
            String ret = compreFaceUtils.addSubject("user02");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateSubject() {
        try {
            String ret = compreFaceUtils.updateSubject("user01", "user03");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteSubject() {
        try {
            String ret = compreFaceUtils.deleteSubject("user02");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteAllSubject() {
        try {
            String ret = compreFaceUtils.deleteAllSubject();
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void listSubject() {
        try {
            String ret = compreFaceUtils.listSubject();
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addFace() {
        try {
            String ret = compreFaceUtils.addFace(
                    "user02",
                    null,
                    IOUtils.resourceToString("/CompreFaceUtilsTest_face02.txt", StandardCharsets.UTF_8));
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteFace() {
        try {
            String ret = compreFaceUtils.deleteFace("cd173bf1-4380-4e7b-87ff-939bba91310c");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteFaceAll() {
        try {
            String ret = compreFaceUtils.deleteFaceAll("user02");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteFaceMulti() {
        try {
            String ret = compreFaceUtils.deleteFaceMulti(new String[]{"9bf07a2a-4c25-4388-a260-8d61c28ea0a6"});
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void listFace() {
        try {
            String ret = compreFaceUtils.listFace(null, null, "user02");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void downloadFace() {
        try {
            String imageId = "91b9b033-ee7a-4044-8a67-45619f9b2c00";
            byte[] ret = compreFaceUtils.downloadFace(imageId);
            ByteArrayInputStream buffer = new ByteArrayInputStream(ret);
            BufferedImage bufferedImage = ImageIO.read(buffer);
            ImageIO.write(
                    bufferedImage,
                    "png",
                    new File(String.format("C:/Users/Administrator/Desktop/%s.png", imageId)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void downloadFaceDirect() {
        try {
            String imageId = "2904a713-86db-4c3a-88d5-17ae0b271759";
            byte[] ret = compreFaceUtils.downloadFaceDirect(imageId);
            ByteArrayInputStream buffer = new ByteArrayInputStream(ret);
            BufferedImage bufferedImage = ImageIO.read(buffer);
            ImageIO.write(
                    bufferedImage,
                    "png",
                    new File(String.format("C:/Users/Administrator/Desktop/%s.png", imageId)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void recognizeFace() {
        try {
            String ret = compreFaceUtils.recognizeFace(
                    IOUtils.resourceToString("/CompreFaceUtilsTest_face02.txt", StandardCharsets.UTF_8),
                    null,
                    null,
                    null,
                    null,
                    null);
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void compareFace() {
        try {
            String ret = compreFaceUtils.compareFace(
                    "91b9b033-ee7a-4044-8a67-45619f9b2c00",
                    IOUtils.resourceToString("/CompreFaceUtilsTest_face01.txt", StandardCharsets.UTF_8),
                    null,
                    null,
                    null,
                    null);
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void detectFace() {
        try {
            String ret = compreFaceUtils.detectFace(
                    IOUtils.resourceToString("/CompreFaceUtilsTest_face01.txt", StandardCharsets.UTF_8),
                    null,
                    null,
                    null,
                    null);
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void verifyFace() {
        try {
            String ret = compreFaceUtils.verifyFace(
                    IOUtils.resourceToString("/CompreFaceUtilsTest_face01.txt", StandardCharsets.UTF_8),
                    IOUtils.resourceToString("/CompreFaceUtilsTest_face02.txt", StandardCharsets.UTF_8),
                    null,
                    null,
                    null,
                    null);
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}