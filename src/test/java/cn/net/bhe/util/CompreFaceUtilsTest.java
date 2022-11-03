package cn.net.bhe.util;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;

class CompreFaceUtilsTest {

    @Test
    void addSubject() {
        try {
            String ret = CompreFaceUtils.addSubject("user02");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateSubject() {
        try {
            String ret = CompreFaceUtils.updateSubject("user01", "user03");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteSubject() {
        try {
            String ret = CompreFaceUtils.deleteSubject("user02");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteAllSubject() {
        try {
            String ret = CompreFaceUtils.deleteAllSubject();
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void listSubject() {
        try {
            String ret = CompreFaceUtils.listSubject();
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addFace() {
        try {
            String ret = CompreFaceUtils.addFace(
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
            String ret = CompreFaceUtils.deleteFace("cd173bf1-4380-4e7b-87ff-939bba91310c");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteFaceAll() {
        try {
            String ret = CompreFaceUtils.deleteFaceAll("user02");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteFaceMulti() {
        try {
            String ret = CompreFaceUtils.deleteFaceMulti(new String[]{"9bf07a2a-4c25-4388-a260-8d61c28ea0a6"});
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void listFace() {
        try {
            String ret = CompreFaceUtils.listFace(null, null, "user02");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void downloadFace() {
        try {
            String imageId = "91b9b033-ee7a-4044-8a67-45619f9b2c00";
            byte[] ret = CompreFaceUtils.downloadFace(imageId);
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
            byte[] ret = CompreFaceUtils.downloadFaceDirect(imageId);
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
            String ret = CompreFaceUtils.recognizeFace(
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
    void faceCompare() {
        try {
            String ret = CompreFaceUtils.faceCompare(
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
    void faceDetect() {
        try {
            String ret = CompreFaceUtils.faceDetect(
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
    void faceVerify() {
        try {
            String ret = CompreFaceUtils.faceVerify(
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