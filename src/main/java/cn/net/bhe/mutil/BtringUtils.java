package cn.net.bhe.mutil;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Administrator
 */
public class BtringUtils extends StringUtils {

    /**
     * 获取汉字串拼音首字母，英文字符不变。
     *
     * @param inputString 字符串
     * @return 结果
     * @throws Exception 异常
     */
    public static String getFirstLetter(String inputString) throws Exception {
        StringBuilder ret = new StringBuilder();
        char[] arr = inputString.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : arr) {
            if (c > 128) {
                String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
                ret.append(temp[0].charAt(0));
            } else {
                ret.append(c);
            }
        }
        return ret.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 获取汉字串拼音，英文字符不变。
     *
     * @param inputString 字符串
     * @return 结果
     * @throws Exception 异常
     */
    public static String getFullLetter(String inputString) throws Exception {
        StringBuilder ret = new StringBuilder();
        char[] arr = inputString.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : arr) {
            if (c > 128) {
                ret.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0]);
            } else {
                ret.append(c);
            }
        }
        return ret.toString();
    }

    public static String camelToSnake(String string) {
        return string.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

}
