package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

class DingTalkUtilsTest {

    private static String corpId = "***";
    private static String agentId = "***";
    private static String appKey = "***";
    private static String appSecret = "***";

    private static String accessToken = "***";
    private static String userId = "***";
    private static String unionId = "***";
    private static String jsticket = "***";

    @Test
    void getAccessToken() {
        try {
            String res = DingTalkUtils.getAccessToken(appKey, appSecret);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getUserInfo() {
        try {
            String res = DingTalkUtils.getUserInfo(accessToken, "eec537ca2e3b3392a96e4560c3d22b6d");
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getUserIdByMobile() {
        try {
            String res = DingTalkUtils.getUserIdByMobile(accessToken, "***");
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getUserDetail() {
        try {
            String res = DingTalkUtils.getUserDetail(accessToken, userId);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void sendCorpConversation() {
        try {
            String res = DingTalkUtils.sendCorpConversationMessage(accessToken,
                    new CorpConversationMessage(
                            agentId,
                            CollUtils.map(
                                    "msgtype", "oa",
                                    "message_url", "http://dingtalk.com",
                                    "oa", CollUtils.map(
                                            "head", CollUtils.map("bgcolor", "FFBBBBBB"),
                                            "body", CollUtils.map(
                                                    "title", "***",
                                                    "author", "***",
                                                    "form", CollUtils.map(),
                                                    "rich", CollUtils.map("unit", "元", "num", Math.random()))))
                    ).setUseridList(userId)
            );
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getProgressOfSendingCorpConversationMessage() {
        try {
            String res = DingTalkUtils.getProgressOfSendingCorpConversationMessage(accessToken, agentId, "***");
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void recallCorpConversationMessage() {
        try {
            String res = DingTalkUtils.recallCorpConversationMessage(accessToken, agentId, "***");
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getJsapiTicket() {
        try {
            String res = DingTalkUtils.getJsapiTicket(accessToken);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void sign() {
        try {
            long timestamp = System.currentTimeMillis();
            String nonceStr = DingTalkUtils.getRandomStr(6);
            String res = DingTalkUtils.sign(jsticket, nonceStr, timestamp, "http://127.0.0.1:8080/");
            System.out.println(timestamp);
            System.out.println(nonceStr);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}