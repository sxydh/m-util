package cn.net.bhe.mutil;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.Enumeration;

/**
 * 雪花ID算法简单实现
 *
 * @author sxydh
 * @since 1.0.1
 */
public final class Snowflake {

    /* 符号位数 */
    private static final int SIGN_BITS = 1;
    /* 时间戳位数 */
    private static final int TS_BITS = 41;
    /* 应用标识位数 */
    private static final int NODE_ID_BITS = 10;
    /* 序列号位数 */
    private static final int SEQUENCE_BITS = 12;

    /* 时间戳左移位数 */
    private static final int TS_LSHIFT = NODE_ID_BITS + SEQUENCE_BITS;
    /* 应用标识左移位数 */
    private static final int NODE_ID_LSHIFT = SEQUENCE_BITS;

    /* 默认初始时间戳 */
    private static final long DEFAULT_INIT_TS = 1698827945644L;
    /* 应用标识最大值 */
    private static final long MAX_NODE_ID = (1L << NODE_ID_BITS) - 1;
    /* 序列号最大值 */
    private static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;

    private final long initTs;
    private final long nodeId;

    private volatile long lastTs = -1L;
    private volatile long sequence = 0L;

    public Snowflake() {
        this.initTs = DEFAULT_INIT_TS;
        this.nodeId = createNodeId();
    }

    public Snowflake(long nodeId) {
        this(DEFAULT_INIT_TS, nodeId);
    }

    public Snowflake(long initTs, long nodeId) {
        if (nodeId < 0 || nodeId > MAX_NODE_ID) {
            throw new IllegalArgumentException(String.format("NodeId must be between %d and %d", 0, MAX_NODE_ID));
        }
        this.initTs = initTs;
        this.nodeId = nodeId;
    }

    public synchronized long nextId() {
        long currentTs = getTs();

        if (currentTs < lastTs) {
            throw new IllegalStateException(String.format("Clock is moving backwards. Rejecting requests until %d.", lastTs));
        }

        if (currentTs == lastTs) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                currentTs = getNextTs(currentTs);
            }
        } else {
            sequence = 0;
        }

        lastTs = currentTs;

        return (currentTs - initTs) << TS_LSHIFT
                | (nodeId << NODE_ID_LSHIFT)
                | sequence;
    }

    private long getTs() {
        return System.currentTimeMillis();
    }

    private long getNextTs(long currentTs) {
        while (currentTs == lastTs) {
            currentTs = getTs();
        }
        return currentTs;
    }

    private long createNodeId() {
        long nodeId;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] hardwareAddress = networkInterface.getHardwareAddress();
                if (hardwareAddress != null) {
                    for (byte ele : hardwareAddress) {
                        sb.append(String.format("%02X", ele));
                    }
                }
            }
            nodeId = sb.toString().hashCode();
        } catch (Exception ex) {
            nodeId = (new SecureRandom().nextInt());
        }
        nodeId = nodeId & MAX_NODE_ID;
        return nodeId;
    }

}
