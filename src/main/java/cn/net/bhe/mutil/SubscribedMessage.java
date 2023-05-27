package cn.net.bhe.mutil;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class SubscribedMessage {

    @NonNull
    private String touser;
    @NonNull
    private String templateId;
    private String page;
    @NonNull
    private Map<String, Object> data;
    private String miniprogramState;
    private String lang;

}
