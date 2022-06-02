package cn.net.bhe.util;

import com.alibaba.fastjson.JSON;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import org.junit.jupiter.api.Test;

class AliSmsUtilsTest {

    @Test
    void sendSms() {
        try {
            SendSmsResponse sendSmsResponse = AliSmsUtils.sendSms(
                    "***",
                    "***",
                    "***",
                    "***",
                    "***",
                    "{\"code\":\"123456\"}");
            System.out.println(JSON.toJSONString(sendSmsResponse.getBody()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test() {
    }

}