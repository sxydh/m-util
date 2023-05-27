package cn.net.bhe.mutil;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @see <a href="https://help.aliyun.com/document_detail/215759.html">原版 SDK</a>
 * @see <a href="https://help.aliyun.com/document_detail/215759.html">升级版 SDK</a>
 */
public class AliSmsUtils {

    /**
     * 发送短信
     *
     * @param accessKeyId     @see <a href="https://ram.console.aliyun.com/users/bhealisms">RAM 访问控制</a>
     * @param accessKeySecret @see <a href="https://ram.console.aliyun.com/users/bhealisms">RAM 访问控制</a>
     * @param phoneNumbers    接收短信的手机号码
     * @param signName        @see <a href="https://dysms.console.aliyun.com/domestic/text/sign">签名</a>
     * @param templateCode    @see <a href="https://dysms.console.aliyun.com/domestic/text/template">模板</a>
     * @param templateParam   模板参数
     * @return SendSmsResponse
     */
    public static SendSmsResponse sendSms(String accessKeyId, String accessKeySecret, String phoneNumbers, String signName, String templateCode, String templateParam) throws Exception {
        Client client = createClient(accessKeyId, accessKeySecret);
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setPhoneNumbers(phoneNumbers);
        sendSmsRequest.setSignName(signName);
        sendSmsRequest.setTemplateCode(templateCode);
        if (ObjectUtils.isNotEmpty(templateParam)) {
            sendSmsRequest.setTemplateParam(templateParam);
        }
        return client.sendSms(sendSmsRequest);
    }

    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

}


