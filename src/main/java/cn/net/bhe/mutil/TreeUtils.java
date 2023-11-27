package cn.net.bhe.mutil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("CallToPrintStackTrace")
public class TreeUtils {

    public static List<String> pathList(Collection<?> nodeList, String valueName, String childrenName) {
        return pathList(nodeList, valueName, childrenName, StrUtils.EMPTY);
    }

    public static List<String> pathList(Collection<?> nodeList, String valueName, String childrenName, String separator) {
        if (CollUtils.isEmpty(nodeList)) {
            return new ArrayList<>();
        }
        try {
            Class<?> clazz = nodeList.iterator().next().getClass();
            Field valuef = clazz.getDeclaredField(valueName);
            Field childrenf = clazz.getDeclaredField(childrenName);
            valuef.setAccessible(true);
            childrenf.setAccessible(true);
            List<String> res = new ArrayList<>();
            for (Object node : nodeList) {
                doPathList(node, valuef, childrenf, new StringBuilder(), separator, res);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @SuppressWarnings("unchecked")
    public static void doPathList(Object node, Field valuef, Field childrenf, StringBuilder path, String separator, List<String> res) throws Exception {
        path.append(separator).append(valuef.get(node));
        Collection<Object> children = (Collection<Object>) childrenf.get(node);
        if (CollUtils.isEmpty(children)) {
            res.add(path.substring(separator.length()));
            path.delete(path.lastIndexOf(separator), path.length());
            return;
        }
        for (Object child : children) {
            doPathList(child, valuef, childrenf, path, separator, res);
        }
    }

}
