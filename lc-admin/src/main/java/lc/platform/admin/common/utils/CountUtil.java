package lc.platform.admin.common.utils;

/**
 * 数字相关的工具类
 */
public class CountUtil {

    public static String formartCount(String count) {

        String[] s1 = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] s2 = {"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};

        String result = "";

        int n = count.length();
        for (int i = 0; i < n; i++) {

            int num = count.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
        }

        return result;
    }

    public static String formartCount(int countNum) {

        String count = String.valueOf(countNum);

        String[] s1 = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] s2 = {"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};

        String result = "";

        int n = count.length();
        for (int i = 0; i < n; i++) {

            int num = count.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
        }

        return result;
    }

}