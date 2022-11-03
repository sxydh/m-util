package cn.net.bhe.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeader;

import java.net.URI;
import java.util.*;

/**
 * @author Administrator
 * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md">Rest API description</a>
 */
public class CompreFaceUtils {

    /**
     * 服务器
     */
    private static final String SERVER = "http://172.18.5.138:8000/";

    /**
     * 检测服务KEY
     */
    private static final String XAK_FD = "eda569dc-64ef-4338-a1a3-7c633f5f928c";
    /**
     * 比对服务KEY
     */
    private static final String XAK_FV = "37240f60-4d10-4db3-89d2-fcccd8c1d6c2";
    /**
     * 识别服务KEY
     */
    private static final String XAK_FR = "74b97ce2-031b-4062-a591-9929df7a0f9a";

    /**
     * 添加主体
     */
    private static final String API_SBJ_ADD = SERVER + "/api/v1/recognition/subjects";
    /**
     * 更新主体
     */
    private static final String API_SBJ_UPDATE = SERVER + "/api/v1/recognition/subjects/%s";
    /**
     * 删除主体
     */
    private static final String API_SBJ_DEL = SERVER + "/api/v1/recognition/subjects/%s";
    /**
     * 删除主体(全量)
     */
    private static final String API_SBJ_DEL_ALL = SERVER + "/api/v1/recognition/subjects";
    /**
     * 主体列表
     */
    private static final String API_SBJ_LIST = SERVER + "/api/v1/recognition/subjects";

    /**
     * 添加人脸
     */
    private static final String API_FC_ADD = SERVER + "/api/v1/recognition/faces";
    /**
     * 删除人脸
     */
    private static final String API_FC_DEL = SERVER + "/api/v1/recognition/faces/%s";
    /**
     * 删除人脸(主体)
     */
    private static final String API_FC_DEL_ALL = SERVER + "/api/v1/recognition/faces";
    /**
     * 删除人脸(批量)
     */
    private static final String API_FC_DEL_MULTI = SERVER + "/api/v1/recognition/faces/delete";
    /**
     * 人脸列表
     */
    private static final String API_FC_LIST = SERVER + "/api/v1/recognition/faces";
    /**
     * 人脸下载
     */
    private static final String API_FC_DL = SERVER + "/api/v1/recognition/faces/%s/img";
    /**
     * 人脸下载(不带Header)
     */
    private static final String API_FC_DL_DIRECT = SERVER + "/api/v1/static/%s/images/%s";
    /**
     * 人脸搜索
     */
    private static final String API_FC_RCN = SERVER + "/api/v1/recognition/recognize";
    /**
     * 人脸比对
     */
    private static final String API_FC_CMP = SERVER + "/api/v1/recognition/faces/%s/verify";

    /**
     * 人脸检测
     */
    private static final String API_FDTC = SERVER + "/api/v1/detection/detect";
    /**
     * 人脸比对
     */
    private static final String API_FCMP = SERVER + "/api/v1/verification/verify";

    private static final String HDN_CT = "Content-Type";
    private static final String HDN_XAK = "x-api-key";

    private static final String HDV_AJ = "application/json";

    private static Header[] buildHeaders(String... kvs) {
        List<Header> headers = new ArrayList<>();
        Queue<String> qkvs = new LinkedList<>(Arrays.asList(kvs));
        while (!qkvs.isEmpty()) {
            String k = qkvs.poll();
            String v = qkvs.poll();
            headers.add(new BasicHeader(k, v));
        }
        return headers.toArray(Header[]::new);
    }

    /**
     * Add a Subject
     *
     * @param subject is the name of the subject. It can be a person name, but it can be any string.
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#add-a-subject">Add a Subject</a>
     */
    public static String addSubject(String subject) throws Exception {
        return HttpClientUtils.post(
                new URI(API_SBJ_ADD),
                buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, XAK_FR),
                Map.of("subject", subject));
    }

    /**
     * Rename a Subject
     *
     * @param oldSubject is the name of the oldSubject. It can be a person name, but it can be any string.
     * @param newSubject is the name of the newSubject. It can be a person name, but it can be any string.
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#rename-a-subject">Rename a Subject</a>
     */
    public static String updateSubject(String oldSubject, String newSubject) throws Exception {
        return HttpClientUtils.put(
                new URI(String.format(API_SBJ_UPDATE, oldSubject)),
                buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, XAK_FR),
                Map.of("subject", newSubject));
    }

    /**
     * Delete a Subject
     *
     * @param subject is the name of the subject. It can be a person name, but it can be any string.
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#delete-all-subjects">Delete All Subjects</a>
     */
    public static String deleteSubject(String subject) throws Exception {
        return HttpClientUtils.delete(
                new URI(String.format(API_SBJ_DEL, subject)),
                buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, XAK_FR));
    }

    /**
     * Delete All Subjects
     *
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#delete-all-subjects">Delete All Subjects</a>
     */
    public static String deleteAllSubject() throws Exception {
        return HttpClientUtils.delete(
                new URI(API_SBJ_DEL_ALL),
                buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, XAK_FR));
    }

    /**
     * List Subjects
     *
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#list-subjects">List Subjects</a>
     */
    public static String listSubject() throws Exception {
        return HttpClientUtils.get(
                new URI(API_SBJ_LIST),
                buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, XAK_FR));
    }

    /**
     * Add an Example of a Subject
     *
     * @param subject          is the name you assign to the image you save
     * @param detProbThreshold minimum required confidence that a recognized face is actually a face. Value is between 0.0 and 1.0.
     * @param file             allowed image formats: jpeg, jpg, ico, png, bmp, gif, tif, tiff, webp. Max size is 5Mb.
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#add-an-example-of-a-subject">Add an Example of a Subject</a>
     */
    public static String addFace(String subject, String detProbThreshold, String file) throws Exception {
        return HttpClientUtils.post(
                new URIBuilder(API_FC_ADD)
                        .addParameter("subject", subject)
                        .addParameter("det_prob_threshold", detProbThreshold)
                        .build(),
                buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, XAK_FR),
                Map.of("file", file));
    }

    /**
     * Delete an Example of the Subject by ID
     *
     * @param imageId UUID of the removing face
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#delete-an-example-of-the-subject-by-id">Delete an Example of the Subject by ID</a>
     */
    public static String deleteFace(String imageId) throws Exception {
        return HttpClientUtils.delete(
                new URI(String.format(API_FC_DEL, imageId)),
                buildHeaders(HDN_XAK, XAK_FR));
    }

    /**
     * Delete All Examples of the Subject by Name
     *
     * @param subject is the name subject. If this parameter is absent, all faces in Face Collection will be removed
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#delete-all-examples-of-the-subject-by-name">Delete All Examples of the Subject by Name</a>
     */
    public static String deleteFaceAll(String subject) throws Exception {
        return HttpClientUtils.delete(
                new URIBuilder(API_FC_DEL_ALL)
                        .addParameter("subject", subject)
                        .build(),
                buildHeaders(HDN_XAK, XAK_FR));
    }

    /**
     * Delete Multiple Examples
     *
     * @param imageIds UUID array of the removing faces
     * @return resul
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#delete-multiple-examples">Delete Multiple Examples</a>
     */
    public static String deleteFaceMulti(String[] imageIds) throws Exception {
        return HttpClientUtils.post(
                new URI(API_FC_DEL_MULTI),
                buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, XAK_FR),
                JSON.toJSONString(imageIds));
    }

    /**
     * List of All Saved Examples of the Subject
     *
     * @param page    page number of examples to return. Can be used for pagination. Default value is 0. Since 0.6 version.
     * @param size    faces on page (page size). Can be used for pagination. Default value is 20. Since 0.6 version.
     * @param subject what subject examples endpoint should return. If empty, return examples for all subjects. Since 1.0 version.
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#list-of-all-saved-examples-of-the-subject">List of All Saved Examples of the Subject</a>
     */
    public static String listFace(String page, String size, String subject) throws Exception {
        return HttpClientUtils.get(
                new URIBuilder(API_FC_LIST)
                        .addParameter("page", page)
                        .addParameter("size", size)
                        .addParameter("subject", subject)
                        .build(),
                buildHeaders(HDN_XAK, XAK_FR));
    }

    /**
     * Download an Image example of the Subject by ID
     *
     * @param imageId UUID of the image to download
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#download-an-image-example-of-the-subject-by-id">Download an Image example of the Subject by ID</a>
     */
    public static byte[] downloadFace(String imageId) throws Exception {
        return HttpClientUtils.getAsBytes(
                new URI(String.format(API_FC_DL, imageId)),
                buildHeaders(HDN_XAK, XAK_FR));
    }

    /**
     * Direct Download an Image example of the Subject by ID
     *
     * @param imageId UUID of the image to download
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#direct-download-an-image-example-of-the-subject-by-id">Direct Download an Image example of the Subject by ID</a>
     */
    public static byte[] downloadFaceDirect(String imageId) throws Exception {
        return HttpClientUtils.getAsBytes(
                new URI(String.format(API_FC_DL_DIRECT, XAK_FR, imageId)));
    }

    /**
     * Recognize Faces from a Given Image
     *
     * @param file             allowed image formats: jpeg, jpg, ico, png, bmp, gif, tif, tiff, webp. Max size is 5Mb.
     * @param limit            maximum number of faces on the image to be recognized. It recognizes the biggest faces first. Value of 0 represents no limit. Default value: 0.
     * @param detProbThreshold minimum required confidence that a recognized face is actually a face. Value is between 0.0 and 1.0.
     * @param predictionCount  maximum number of subject predictions per face. It returns the most similar subjects. Default value: 1.
     * @param facePlugins      comma-separated slugs of face plugins. If empty, no additional information is returned.
     * @param status           if true includes system information like execution_time and plugin_version fields. Default value is false
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#recognize-faces-from-a-given-image">Recognize Faces from a Given Image</a>
     */
    public static String recognizeFace(String file, String limit, String detProbThreshold, String predictionCount, String facePlugins, String status) throws Exception {
        return HttpClientUtils.post(
                new URIBuilder(API_FC_RCN)
                        .addParameter("limit", limit)
                        .addParameter("det_prob_threshold", detProbThreshold)
                        .addParameter("prediction_count", predictionCount)
                        .addParameter("face_plugins", facePlugins)
                        .addParameter("status", status)
                        .build(),
                buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, XAK_FR),
                Map.of("file", file));
    }

    /**
     * Verify Faces from a Given Image
     *
     * @param imageId          UUID of the verifying face
     * @param file             allowed image formats: jpeg, jpg, ico, png, bmp, gif, tif, tiff, webp. Max size is 5Mb.
     * @param limit            maximum number of faces on the target image to be recognized. It recognizes the biggest faces first. Value of 0 represents no limit. Default value: 0.
     * @param detProbThreshold minimum required confidence that a recognized face is actually a face. Value is between 0.0 and 1.0.
     * @param facePlugins      comma-separated slugs of face plugins. If empty, no additional information is returned.
     * @param status           if true includes system information like execution_time and plugin_version fields. Default value is false.
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#verify-faces-from-a-given-image">Verify Faces from a Given Image</a>
     */
    public static String compareFace(String imageId, String file, String limit, String detProbThreshold, String facePlugins, String status) throws Exception {
        return HttpClientUtils.post(
                new URIBuilder(String.format(API_FC_CMP, imageId))
                        .addParameter("limit", limit)
                        .addParameter("det_prob_threshold", detProbThreshold)
                        .addParameter("face_plugins", facePlugins)
                        .addParameter("status", status)
                        .build(),
                buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, XAK_FR),
                Map.of("file", file));
    }

    /**
     * Face Detection Service
     *
     * @param file             image where to detect faces. Allowed image formats: jpeg, jpg, ico, png, bmp, gif, tif, tiff, webp. Max size is 5Mb.
     * @param limit            maximum number of faces on the image to be recognized. It recognizes the biggest faces first. Value of 0 represents no limit. Default value: 0.
     * @param detProbThreshold minimum required confidence that a recognized face is actually a face. Value is between 0.0 and 1.0.
     * @param facePlugins      comma-separated slugs of face plugins. If empty, no additional information is returned.
     * @param status           if true includes system information like execution_time and plugin_version fields. Default value is false.
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#face-detection-service">Face Detection Service</a>
     */
    public static String detectFace(String file, String limit, String detProbThreshold, String facePlugins, String status) throws Exception {
        return HttpClientUtils.post(
                new URIBuilder(API_FDTC)
                        .addParameter("limit", limit)
                        .addParameter("det_prob_threshold", detProbThreshold)
                        .addParameter("face_plugins", facePlugins)
                        .addParameter("status", status)
                        .build(),
                buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, XAK_FD),
                Map.of("file", file));
    }

    /**
     * Face Verification Service
     *
     * @param sourceImage      file to be verified. Allowed image formats: jpeg, jpg, ico, png, bmp, gif, tif, tiff, webp. Max size is 5Mb.
     * @param targetImage      reference file to check the source file. Allowed image formats: jpeg, jpg, ico, png, bmp, gif, tif, tiff, webp. Max size is 5Mb.
     * @param limit            maximum number of faces on the target image to be recognized. It recognizes the biggest faces first. Value of 0 represents no limit. Default value: 0.
     * @param detProbThreshold minimum required confidence that a recognized face is actually a face. Value is between 0.0 and 1.0.
     * @param facePlugins      comma-separated slugs of face plugins. If empty, no additional information is returned.
     * @param status           if true includes system information like execution_time and plugin_version fields. Default value is false.
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#face-verification-service">Face Verification Service</a>
     */
    public static String verifyFace(String sourceImage, String targetImage, String limit, String detProbThreshold, String facePlugins, String status) throws Exception {
        return HttpClientUtils.post(
                new URIBuilder(API_FCMP)
                        .addParameter("limit", limit)
                        .addParameter("det_prob_threshold", detProbThreshold)
                        .addParameter("face_plugins", facePlugins)
                        .addParameter("status", status)
                        .build(),
                buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, XAK_FV),
                Map.of("source_image", sourceImage,
                        "target_image", targetImage));
    }

}
