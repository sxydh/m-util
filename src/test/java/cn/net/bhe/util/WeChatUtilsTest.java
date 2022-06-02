package cn.net.bhe.util;

import org.junit.jupiter.api.Test;

import java.util.Map;

class WeChatUtilsTest {

    private String appid = "***";
    private String secret = "***";
    private String accessToken = "***";

    @Test
    void getAccessToken() {
        try {
            String ret = WeChatUtils.getAccessToken(appid, secret);
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void jscode2session() {
        try {
            String ret = WeChatUtils.jscode2session(appid, secret, "***");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void sendSubscribedMessage() {
        try {
            String ret = WeChatUtils.sendSubscribedMessage(
                    accessToken,
                    new SubscribedMessage(
                            "***",
                            "***",
                            Map.of("k1", Map.of("value", "***"),
                                    "k2", Map.of("value", "***"),
                                    "k3", Map.of("value", "***"),
                                    "k4", Map.of("value", "***")))
                            .setPage("***")
                            .setMiniprogramState("***")
                            .setLang("zh_CN"));
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}