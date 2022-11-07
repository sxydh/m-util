package cn.net.bhe.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.util.Map;

/**
 * @author Administrator
 * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md">Rest API description</a>
 */
public class CompreFaceUtils {

    /**
     * 服务器
     */
    private final String server;

    /**
     * 检测服务KEY
     */
    private final String xakFd;
    /**
     * 比对服务KEY
     */
    private final String xakFv;
    /**
     * 识别服务KEY
     */
    private final String xakFr;

    /**
     * 添加主体
     */
    private static final String API_SBJ_ADD = "/api/v1/recognition/subjects";
    /**
     * 更新主体
     */
    private static final String API_SBJ_UPDATE = "/api/v1/recognition/subjects/%s";
    /**
     * 删除主体
     */
    private static final String API_SBJ_DEL = "/api/v1/recognition/subjects/%s";
    /**
     * 删除主体(全量)
     */
    private static final String API_SBJ_DEL_ALL = "/api/v1/recognition/subjects";
    /**
     * 主体列表
     */
    private static final String API_SBJ_LIST = "/api/v1/recognition/subjects";

    /**
     * 添加人脸
     */
    private static final String API_FC_ADD = "/api/v1/recognition/faces";
    /**
     * 删除人脸
     */
    private static final String API_FC_DEL = "/api/v1/recognition/faces/%s";
    /**
     * 删除人脸(主体)
     */
    private static final String API_FC_DEL_ALL = "/api/v1/recognition/faces";
    /**
     * 删除人脸(批量)
     */
    private static final String API_FC_DEL_MULTI = "/api/v1/recognition/faces/delete";
    /**
     * 人脸列表
     */
    private static final String API_FC_LIST = "/api/v1/recognition/faces";
    /**
     * 人脸下载
     */
    private static final String API_FC_DL = "/api/v1/recognition/faces/%s/img";
    /**
     * 人脸下载(不带Header)
     */
    private static final String API_FC_DL_DIRECT = "/api/v1/static/%s/images/%s";
    /**
     * 人脸搜索
     */
    private static final String API_FC_RCN = "/api/v1/recognition/recognize";
    /**
     * 人脸比对
     */
    private static final String API_FC_CMP = "/api/v1/recognition/faces/%s/verify";

    /**
     * 人脸检测
     */
    private static final String API_FDTC = "/api/v1/detection/detect";
    /**
     * 人脸比对
     */
    private static final String API_FCMP = "/api/v1/verification/verify";

    private static final String HDN_CT = "Content-Type";
    private static final String HDN_XAK = "x-api-key";

    private static final String HDV_AJ = "application/json";

    public CompreFaceUtils(
            String server,
            String xakFd,
            String xakFv,
            String xakFr) {
        this.server = server;
        this.xakFd = xakFd;
        this.xakFv = xakFv;
        this.xakFr = xakFr;
    }

    /**
     * Add a Subject
     *
     * @param subject is the name of the subject. It can be a person name, but it can be any string.
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#add-a-subject">Add a Subject</a>
     */
    public String addSubject(String subject) throws Exception {
        return HttpClientUtils.post(
                new URI(server + API_SBJ_ADD),
                HttpClientUtils.buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, xakFr),
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
    public String updateSubject(String oldSubject, String newSubject) throws Exception {
        return HttpClientUtils.put(
                new URI(server + String.format(API_SBJ_UPDATE, oldSubject)),
                HttpClientUtils.buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, xakFr),
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
    public String deleteSubject(String subject) throws Exception {
        return HttpClientUtils.delete(
                new URI(server + String.format(API_SBJ_DEL, subject)),
                HttpClientUtils.buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, xakFr));
    }

    /**
     * Delete All Subjects
     *
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#delete-all-subjects">Delete All Subjects</a>
     */
    public String deleteAllSubject() throws Exception {
        return HttpClientUtils.delete(
                new URI(server + API_SBJ_DEL_ALL),
                HttpClientUtils.buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, xakFr));
    }

    /**
     * List Subjects
     *
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#list-subjects">List Subjects</a>
     */
    public String listSubject() throws Exception {
        return HttpClientUtils.get(
                new URI(server + API_SBJ_LIST),
                HttpClientUtils.buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, xakFr));
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
    public String addFace(String subject, String detProbThreshold, String file) throws Exception {
        return HttpClientUtils.post(
                new URIBuilder(server + API_FC_ADD)
                        .addParameter("subject", subject)
                        .addParameter("det_prob_threshold", detProbThreshold)
                        .build(),
                HttpClientUtils.buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, xakFr),
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
    public String deleteFace(String imageId) throws Exception {
        return HttpClientUtils.delete(
                new URI(server + String.format(API_FC_DEL, imageId)),
                HttpClientUtils.buildHeaders(HDN_XAK, xakFr));
    }

    /**
     * Delete All Examples of the Subject by Name
     *
     * @param subject is the name subject. If this parameter is absent, all faces in Face Collection will be removed
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#delete-all-examples-of-the-subject-by-name">Delete All Examples of the Subject by Name</a>
     */
    public String deleteFaceAll(String subject) throws Exception {
        return HttpClientUtils.delete(
                new URIBuilder(server + API_FC_DEL_ALL)
                        .addParameter("subject", subject)
                        .build(),
                HttpClientUtils.buildHeaders(HDN_XAK, xakFr));
    }

    /**
     * Delete Multiple Examples
     *
     * @param imageIds UUID array of the removing faces
     * @return resul
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#delete-multiple-examples">Delete Multiple Examples</a>
     */
    public String deleteFaceMulti(String[] imageIds) throws Exception {
        return HttpClientUtils.post(
                new URI(server + API_FC_DEL_MULTI),
                HttpClientUtils.buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, xakFr),
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
    public String listFace(String page, String size, String subject) throws Exception {
        return HttpClientUtils.get(
                new URIBuilder(server + API_FC_LIST)
                        .addParameter("page", page)
                        .addParameter("size", size)
                        .addParameter("subject", subject)
                        .build(),
                HttpClientUtils.buildHeaders(HDN_XAK, xakFr));
    }

    /**
     * Download an Image example of the Subject by ID
     *
     * @param imageId UUID of the image to download
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#download-an-image-example-of-the-subject-by-id">Download an Image example of the Subject by ID</a>
     */
    public byte[] downloadFace(String imageId) throws Exception {
        return HttpClientUtils.getAsBytes(
                new URI(server + String.format(API_FC_DL, imageId)),
                HttpClientUtils.buildHeaders(HDN_XAK, xakFr));
    }

    /**
     * Direct Download an Image example of the Subject by ID
     *
     * @param imageId UUID of the image to download
     * @return result
     * @throws Exception exception
     * @see <a href="https://github.com/exadel-inc/CompreFace/blob/master/docs/Rest-API-description.md#direct-download-an-image-example-of-the-subject-by-id">Direct Download an Image example of the Subject by ID</a>
     */
    public byte[] downloadFaceDirect(String imageId) throws Exception {
        return HttpClientUtils.getAsBytes(
                new URI(server + String.format(API_FC_DL_DIRECT, xakFr, imageId)));
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
    public String recognizeFace(String file, String limit, String detProbThreshold, String predictionCount, String facePlugins, String status) throws Exception {
        return HttpClientUtils.post(
                new URIBuilder(server + API_FC_RCN)
                        .addParameter("limit", limit)
                        .addParameter("det_prob_threshold", detProbThreshold)
                        .addParameter("prediction_count", predictionCount)
                        .addParameter("face_plugins", facePlugins)
                        .addParameter("status", status)
                        .build(),
                HttpClientUtils.buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, xakFr),
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
    public String compareFace(String imageId, String file, String limit, String detProbThreshold, String facePlugins, String status) throws Exception {
        return HttpClientUtils.post(
                new URIBuilder(server + String.format(API_FC_CMP, imageId))
                        .addParameter("limit", limit)
                        .addParameter("det_prob_threshold", detProbThreshold)
                        .addParameter("face_plugins", facePlugins)
                        .addParameter("status", status)
                        .build(),
                HttpClientUtils.buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, xakFr),
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
    public String detectFace(String file, String limit, String detProbThreshold, String facePlugins, String status) throws Exception {
        return HttpClientUtils.post(
                new URIBuilder(server + API_FDTC)
                        .addParameter("limit", limit)
                        .addParameter("det_prob_threshold", detProbThreshold)
                        .addParameter("face_plugins", facePlugins)
                        .addParameter("status", status)
                        .build(),
                HttpClientUtils.buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, xakFd),
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
    public String verifyFace(String sourceImage, String targetImage, String limit, String detProbThreshold, String facePlugins, String status) throws Exception {
        return HttpClientUtils.post(
                new URIBuilder(server + API_FCMP)
                        .addParameter("limit", limit)
                        .addParameter("det_prob_threshold", detProbThreshold)
                        .addParameter("face_plugins", facePlugins)
                        .addParameter("status", status)
                        .build(),
                HttpClientUtils.buildHeaders(
                        HDN_CT, HDV_AJ,
                        HDN_XAK, xakFv),
                Map.of("source_image", sourceImage,
                        "target_image", targetImage));
    }

}
