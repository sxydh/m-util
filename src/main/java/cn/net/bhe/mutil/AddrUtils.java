package cn.net.bhe.mutil;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddrUtils {

    private static final List<Node> CHN_LIST;
    private static final Random RANDOM = new Random();

    static {
        try {
            Node node = new Node();
            Node.parseList(ZipUtils.deComp("AddrUtils.CHN_LIST"), 0, node);
            CHN_LIST = node.getChildren();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String ranChn(int depth) {
        return ranChn(depth, StrUtils.SLASH);
    }

    public static String ranChn(int depth, String separator) {
        if (depth <= 0) return StrUtils.EMPTY;
        StringBuilder builder = new StringBuilder();
        List<Node> nodes = CHN_LIST;
        for (int i = 1; i <= depth; i++) {
            if (CollUtils.isEmpty(nodes)) break;
            Node node = nodes.get(RANDOM.nextInt(nodes.size()));
            builder.append(separator).append(node.name);
            nodes = node.getChildren();
        }
        return builder.deleteCharAt(0).toString();
    }

    @Data
    @Accessors(chain = true)
    private static class Node {
        private String code;
        private String name;
        private List<Node> children = new ArrayList<>();

        private static int parseList(String jsonStr, int i, Node node) {
            if (jsonStr.charAt(i) != '[') return i;
            i++;
            while (jsonStr.charAt(i) != ']') {
                if (jsonStr.charAt(i) == ',') {
                    i++;
                }
                i = parseNode(jsonStr, i, node);
            }
            return i + 1;
        }

        private static int parseNode(String jsonStr, int i, Node node) {
            if (jsonStr.charAt(i) != '{') return i;
            i++;
            Node newNode = new Node();
            String key = null;
            StringBuilder builder = new StringBuilder();
            while (jsonStr.charAt(i) != '}') {
                if (jsonStr.charAt(i) == ',' || jsonStr.charAt(i) == ':') {
                    i++;
                    continue;
                }
                i = parseString(jsonStr, i, builder);
                if (key == null) {
                    key = builder.toString();
                } else {
                    i = parseString(jsonStr, i, builder);
                    switch (key) {
                        case "code":
                            newNode.setCode(builder.toString());
                            break;
                        case "name":
                            newNode.setName(builder.toString());
                            break;
                        case "children":
                            i = parseList(jsonStr, i, newNode);
                            break;
                    }
                    key = null;
                }
                builder.setLength(0);
            }
            node.getChildren().add(newNode);
            return i + 1;
        }

        private static int parseString(String jsonStr, int i, StringBuilder builder) {
            if (jsonStr.charAt(i) != '"') return i;
            while (jsonStr.charAt(++i) != '"') {
                builder.append(jsonStr.charAt(i));
            }
            return i + 1;
        }
    }

}
