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
public class CorpConversationMessage {

    /**
     * 发送消息时使用的微应用的AgentID.
     */
    @NonNull
    private String agentId;

    /**
     * 接收者的userid列表，最大用户列表长度100.
     */
    private String useridList;

    /**
     * 接收者的部门id列表，最大列表长度20. 接收者是部门ID时，包括子部门下的所有用户.
     */
    private String deptIdList;

    /**
     * 是否发送给企业全部用户.
     */
    private String toAllUser;

    /**
     * 消息内容，最长不超过2048个字节.
     */
    @NonNull
    private Map<String, Object> msg;

}
