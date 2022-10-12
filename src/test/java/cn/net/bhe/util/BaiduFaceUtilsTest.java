package cn.net.bhe.util;

import com.baidu.aip.face.AipFace;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class BaiduFaceUtilsTest {

    @Test
    void test1() throws Exception {
        // 初始化一个AipFace
        AipFace client = new AipFace("27681599", "0uNN7sRHLEVcP0wcadZ7Bdrm", "WQQdlUP19Dl0p6LzEDOfiBjLNpjoqNii");

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

//        // 传入可选参数调用接口
//        HashMap<String, String> options = new HashMap<String, String>();
//        options.put("appid", "27681599");
//        // 语音校验码接口
//        JSONObject res = client.videoSessioncode(options);
//        System.out.println(res.toString(2));

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        String sessionId = "S633539f1c7dae449211096";
        byte[] file = FileUtils.readFileToByteArray(new File("C:\\Users\\Administrator\\Desktop\\VID_20220929_113322828.mp4"));
        JSONObject res = client.videoFaceliveness(sessionId, file, options);
        System.out.println(res.toString(2));
    }
}