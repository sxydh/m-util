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
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据来源：<a href="http://www.stats.gov.cn/sj/tjbz/qhdm/">统计用区划代码和城乡划分代码</a>，<a href="https://github.com/modood/Administrative-divisions-of-China">Administrative-divisions-of-China</a>。
 */
@SuppressWarnings("CallToPrintStackTrace")
public class AddrUtils {

    private static final String URL_TJYQHDMHCXHFDM = "http://www.stats.gov.cn/sj/tjbz/tjyqhdmhcxhfdm/" + DtUtils.format().substring(0, 4);
    private static final Object INIT_LOCK = new Object();
    private static final int[] INIT_CONFIG = new int[]{3, 3, 3, 3, -1};
    private static final Random RANDOM = new Random();
    private static List<Node> nodeList = null;
    private static List<String> addrList = null;

    public static String randomAddr() {
        if (CollUtils.isEmpty(addrList)) {
            return StrUtils.EMPTY;
        }
        return addrList.get(RANDOM.nextInt(addrList.size()));
    }

    public static void init() {
        init(INIT_CONFIG);
    }

    public static void init(int[] config) {
        synchronized (INIT_LOCK) {
            if (nodeList != null) {
                return;
            }
            As.isTrue(config != null && config.length == INIT_CONFIG.length);
            System.arraycopy(config, 0, INIT_CONFIG, 0, config.length);
            String res = get(URL_TJYQHDMHCXHFDM + "/index.html");
            List<Node> provinceList = parseHtml(res, "provincetr", false, true);
            initProvince(provinceList);
            nodeList = provinceList;
            addrList = TreeUtils.pathList(nodeList, "name", "children", StrUtils.SLASH);
        }
    }

    public static List<Node> get() {
        return nodeList;
    }

    public static void clear() {
        nodeList = null;
        addrList = null;
    }

    private static void initProvince(List<Node> provinceList) {
        while (INIT_CONFIG[0] != -1 && INIT_CONFIG[0] < provinceList.size()) {
            provinceList.remove(RANDOM.nextInt(provinceList.size()));
        }
        for (Node province : provinceList) {
            if (StrUtils.isEmpty(province.getHref())) {
                continue;
            }
            String res = get(URL_TJYQHDMHCXHFDM + "/" + province.getHref());
            List<Node> cityList = parseHtml(res, "citytr");
            initCity(province, cityList);
            province.setChildren(cityList);
        }
    }

    private static void initCity(Node province, List<Node> cityList) {
        while (INIT_CONFIG[1] != -1 && INIT_CONFIG[1] < cityList.size()) {
            cityList.remove(RANDOM.nextInt(cityList.size()));
        }
        for (Node city : cityList) {
            if (StrUtils.isEmpty(city.getHref())) {
                continue;
            }
            String res = get(URL_TJYQHDMHCXHFDM + "/" + city.getHref());
            List<Node> countyList = parseHtml(res, "countytr");
            initCounty(province, city, countyList);
            city.setChildren(countyList);
        }
    }

    private static void initCounty(Node province, Node city, List<Node> countyList) {
        while (INIT_CONFIG[2] != -1 && INIT_CONFIG[2] < countyList.size()) {
            countyList.remove(RANDOM.nextInt(countyList.size()));
        }
        for (Node county : countyList) {
            if (StrUtils.isEmpty(county.getHref())) {
                continue;
            }
            String res = get(URL_TJYQHDMHCXHFDM + "/" + province.getId() + "/" + county.getHref());
            List<Node> townList = parseHtml(res, "towntr");
            initTown(province, city, townList);
            county.setChildren(townList);
        }
    }

    private static void initTown(Node province, Node city, List<Node> townList) {
        while (INIT_CONFIG[3] != -1 && INIT_CONFIG[3] < townList.size()) {
            townList.remove(RANDOM.nextInt(townList.size()));
        }
        for (Node town : townList) {
            if (StrUtils.isEmpty(town.getHref())) {
                continue;
            }
            String pid = province.getId();
            String cid = city.getId().substring(2, 4);
            String res = get(URL_TJYQHDMHCXHFDM + "/" + pid + "/" + cid + "/" + town.getHref());
            List<Node> villageList = parseHtml(res, "villagetr", true, false);
            initVillage(villageList);
            town.setChildren(villageList);
        }
    }

    private static void initVillage(List<Node> villageList) {
        while (INIT_CONFIG[4] != -1 && INIT_CONFIG[4] < villageList.size()) {
            villageList.remove(RANDOM.nextInt(villageList.size()));
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
