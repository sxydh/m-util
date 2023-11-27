package cn.net.bhe.mutil;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据来源：<a href="http://www.stats.gov.cn/sj/tjbz/qhdm/">统计用区划代码和城乡划分代码</a>，<a href="https://github.com/modood/Administrative-divisions-of-China">Administrative-divisions-of-China</a>。
 */
@SuppressWarnings("CallToPrintStackTrace")
public class AddrUtils {

    private static final String URL_TJYQHDMHCXHFDM = "http://www.stats.gov.cn/sj/tjbz/tjyqhdmhcxhfdm/" + DtUtils.format().substring(0, 4);
    private static final Object INIT_LOCK = new Object();

    public static String randomAddr() {
        return "TODO";
    }

    public static void init() {
        synchronized (INIT_LOCK) {
            String res = get(URL_TJYQHDMHCXHFDM + "/index.html");
            List<Node> provinceList = parseHtml(res, "provincetr", false, true);
            initProvince(provinceList);
        }
    }

    private static void initProvince(List<Node> provinceList) {
        for (Node province : provinceList) {
            String res = get(URL_TJYQHDMHCXHFDM + "/" + province.getHref());
            List<Node> cityList = parseHtml(res, "citytr");
            province.setChildren(cityList);

            initCity(province, cityList);
        }
    }

    private static void initCity(Node province, List<Node> cityList) {
        for (Node city : cityList) {
            String res = get(URL_TJYQHDMHCXHFDM + "/" + city.getHref());
            List<Node> countyList = parseHtml(res, "countytr");
            city.setChildren(countyList);

            initCounty(province, city, countyList);
        }
    }

    private static void initCounty(Node province, Node city, List<Node> countyList) {
        for (Node county : countyList) {
            String res = get(URL_TJYQHDMHCXHFDM + "/" + province.getId() + "/" + county.getHref());
            List<Node> townList = parseHtml(res, "towntr");
            county.setChildren(townList);

            initTown(province, city, townList);
        }
    }

    private static void initTown(Node province, Node city, List<Node> townList) {
        for (Node town : townList) {
            String pid = province.getId();
            String cid = StrUtils.trimRight(city.getId(), StrUtils.ZERO);
            cid = cid.substring(province.getId().length());
            String res = get(URL_TJYQHDMHCXHFDM + "/" + pid + "/" + cid + "/" + town.getHref());
            List<Node> villageList = parseHtml(res, "villagetr", true, false);
            town.setChildren(villageList);
        }
    }

    private static List<Node> parseHtml(String html, String classFlag) {
        return parseHtml(html, classFlag, true, true);
    }

    private static List<Node> parseHtml(String html, String classFlag, boolean hasCode, boolean hasHref) {
        String pattern = "(<tr class=\"" + classFlag + "\">)([\\s\\S]+?)(</tr>)";
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(html);
        StringBuilder stringBuilder = new StringBuilder();
        while (matcher.find()) {
            stringBuilder.append(matcher.group(2));
        }

        html = stringBuilder.toString();
        List<Node> nodeList = new ArrayList<>();
        if (hasCode) {
            if (hasHref) {
                pattern = "(href=\")(.+?.html)(\">)(\\d+?)(<)([\\s\\S]+?)(href=\".+?.html\">)([\\u4E00-\\u9FA5]+?)(<)";
                compile = Pattern.compile(pattern);
                matcher = compile.matcher(html);
                while (matcher.find()) {
                    Node node = new Node()
                            .setId(matcher.group(4))
                            .setName(matcher.group(8))
                            .setHref(matcher.group(2));
                    nodeList.add(node);
                }
            } else {
                pattern = "(<td>)(\\d+?)(</td>)([\\s\\S]+?)(<td>)([\\u4E00-\\u9FA5]+?)(</td>)";
                compile = Pattern.compile(pattern);
                matcher = compile.matcher(html);
                while (matcher.find()) {
                    Node node = new Node()
                            .setId(matcher.group(2))
                            .setName(matcher.group(6));
                    nodeList.add(node);
                }
            }
        } else {
            pattern = "(href=\")(.+?)(.html\">)([\\u4E00-\\u9FA5]+?)(<)";
            compile = Pattern.compile(pattern);
            matcher = compile.matcher(html);
            while (matcher.find()) {
                Node node = new Node()
                        .setId(matcher.group(2))
                        .setName(matcher.group(4))
                        .setHref(matcher.group(2) + ".html");
                nodeList.add(node);
            }
        }
        return nodeList;
    }

    private static String get(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            try (InputStream inputStream = conn.getInputStream();
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
                byte[] bytes = new byte[1024];
                StringBuilder stringBuilder = new StringBuilder();
                while (bufferedInputStream.read(bytes) != -1) {
                    stringBuilder.append(new String(bytes, StandardCharsets.UTF_8));
                }
                return stringBuilder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return StrUtils.EMPTY;
        }
    }

    @Data
    @Accessors(chain = true)
    public static class Node {
        private String id;
        private String name;
        private String href;
        private List<Node> children;
    }

}
