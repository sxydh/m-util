package cn.net.bhe.util;

import org.junit.jupiter.api.Test;

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
                            CollUtils.map("k1", CollUtils.map("value", "***"),
                                    "k2", CollUtils.map("value", "***"),
                                    "k3", CollUtils.map("value", "***"),
                                    "k4", CollUtils.map("value", "***")))
                            .setPage("***")
                            .setMiniprogramState("***")
                            .setLang("zh_CN"));
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}