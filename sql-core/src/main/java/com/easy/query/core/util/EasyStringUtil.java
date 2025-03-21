package com.easy.query.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author xuejiaming
 */
public class EasyStringUtil {
    public static final String EMPTY = "";

//    public static final int INDEX_NOT_FOUND = -1;
//
//    public static final String[] EMPTY_STRING_ARRAY = new String[0];


//    static String lineSeparator = System.getProperty("line.separator", "\n");


    // 首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        return Character.toLowerCase(s.charAt(0)) +
                s.substring(1);
    }

    // 首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toUpperCase(s.charAt(0)) +
                    s.substring(1);
        }
    }

    /**
     * 转下划线字符, eg AaaBbb => aaa_bbb
     */
    public static String toLowerUnderlined(String s) {
        char[] chars = s.toCharArray();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (i != 0 && Character.isUpperCase(chars[i])) {
                temp.append("_");
            }
            temp.append(Character.toLowerCase(chars[i]));
        }
        return temp.toString();
    }

    /**
     * 转下划线字符, eg AaaBbb => AAA_BBB
     */
    public static String toUpperUnderlined(String s) {
        char[] chars = s.toCharArray();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (i != 0 && Character.isUpperCase(chars[i])) {
                temp.append("_");
            }
            temp.append(Character.toUpperCase(chars[i]));
        }
        return temp.toString();
    }

    /**
     * 转驼峰式字符，eg: aaa_bbb => aaaBbb , Aaa_bBb=>aaaBBb
     */
    public static String fromLowerUnderlined(String str) {

        String[] splitArr = str.split("_");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < splitArr.length; i++) {
            if (i == 0) {
                sb.append(splitArr[0].toLowerCase());
                continue;
            }

            sb.append(toUpperCaseFirstOne(splitArr[i].toLowerCase()));
        }

        return sb.toString();
    }

    public static String fromAllUpperNoUnderlined(String str) {

        String[] splitArr = str.split("_");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < splitArr.length; i++) {
            sb.append(splitArr[i].toUpperCase());
        }

        return sb.toString();
    }

    public static String fromAllLowerNoUnderlined(String str) {

//        String[] splitArr = str.split("_");
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < splitArr.length; i++) {
//            sb.append(splitArr[i].toLowerCase());
//        }
//
//        return sb.toString();
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != '_') {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    public static String fromAllLowerWithUnderlined(String str) {

        String[] splitArr = str.split("_");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < splitArr.length; i++) {
            if (i != 0) {
                sb.append("_");
            }
            sb.append(splitArr[i].toLowerCase());
        }

        return sb.toString();
    }

    public static String fromAllUpperWithUnderlined(String str) {

        String[] splitArr = str.split("_");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < splitArr.length; i++) {
            if (i != 0) {
                sb.append("_");
            }
            sb.append(splitArr[i].toUpperCase());
        }

        return sb.toString();
    }

    private static final Pattern LEADING_WHITESPACE = Pattern.compile("^\\s+");
    private static final Pattern TRAILING_WHITESPACE = Pattern.compile("\\s+$");

    /**
     * 移除字符串前后空格
     * @param input
     * @return
     */
    public static String trimOuterWhitespaceOptimized(String input) {
        if (input == null) return null;
        String tmp = LEADING_WHITESPACE.matcher(input).replaceAll("");
        return TRAILING_WHITESPACE.matcher(tmp).replaceAll("");
    }



    private static final Pattern NEWLINE_PATTERN = Pattern.compile("[\\r\\n]+");

    /**
     * 将字符串换行转成空格单行显示
     * @param input
     * @return
     */
    public static String convertToSingleLineOptimized(String input) {
        if (input == null) return null;
        return NEWLINE_PATTERN.matcher(input).replaceAll(" ").trim();
    }
    /**
     * 去空格
     *
     * @param str
     * @return
     */
    public static String trimAllWhitespace(String str) {
        if (!((CharSequence) str != null && ((CharSequence) str).length() > 0)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }

//    public static String substringBeforeLast(String str, String separator) {
//        if (isEmpty(str) || isEmpty(separator)) {
//            return str;
//        }
//        int pos = str.lastIndexOf(separator);
//        if (pos == INDEX_NOT_FOUND) {
//            return str;
//        }
//        return str.substring(0, pos);
//    }

    public static String defaultIfBank(String value, String def) {
        if (EasyStringUtil.isBlank(value)) {
            return def;
        }
        return value;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlank(String str) {
        if (isEmpty(str)) {
            return true;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static boolean endsWith(String str, String suffix, boolean ignoreCase) {
        if (str == null || suffix == null) {
            return (str == null && suffix == null);
        }
        if (suffix.length() > str.length()) {
            return false;
        }
        int strOffset = str.length() - suffix.length();
        return str.regionMatches(ignoreCase, strOffset, suffix, 0, suffix.length());
    }

    public static boolean startsWith(String str, String prefix, boolean ignoreCase) {
        if (str == null || prefix == null) {
            return (str == null && prefix == null);
        }
        if (prefix.length() > str.length()) {
            return false;
        }
        return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
    }

    public static boolean startsWith(String str, String prefix) {
        if (str == null || prefix == null || !str.startsWith(prefix)) {
            return false;
        }
        return true;
    }

    public static boolean endsWith(String str, String suffix) {
        if (str == null || suffix == null || !str.endsWith(suffix)) {
            return false;
        }
        return true;
    }

    public static String startWithRemove(String str, String prefix) {
        if (!startsWith(str, prefix)) {
            return str;
        }
        return str.substring(prefix.length());
    }

    public static String endWithRemove(String str, String suffix) {
        if (!endsWith(str, suffix)) {
            return str;
        }
        return str.substring(0, str.length() - suffix.length());
    }

    public static String startWithDefault(String str, String prefix, String def) {
        if (startsWith(str, prefix)) {
            return prefix;
        }
        return def;
    }

    public static String endWithDefault(String str, String suffix, String def) {
        if (endsWith(str, suffix)) {
            return suffix;
        }
        return def;
    }
//
//    public static String substringAfter(String str, String separator) {
//        if (isEmpty(str)) {
//            return str;
//        }
//        if (separator == null) {
//            return EMPTY;
//        }
//        int pos = str.indexOf(separator);
//        if (pos == INDEX_NOT_FOUND) {
//            return EMPTY;
//        }
//        return str.substring(pos + separator.length());
//    }


//    public static boolean isBlank(final CharSequence cs) {
//        int strLen;
//        if (cs == null || (strLen = cs.length()) == 0) {
//            return true;
//        }
//        for (int i = 0; i < strLen; i++) {
//            if (Character.isWhitespace(cs.charAt(i)) == false) {
//                return false;
//            }
//        }
//        return true;
//    }

//
//    /**
//     * 判断一个 Object 是否为空，不包含集合对象的判断
//     *
//     * @param obj need to determine the object
//     * @return
//     * @author larrykoo
//     */
//    public static boolean isNullOrEmpty(Object obj) {
//        if (obj == null) {
//            return true;
//        }
//        if (obj instanceof CharSequence) {
//            return ((CharSequence) obj).length() == 0;
//        }
//        return false;
//    }

//    /**
//     * 严格判断一个 Object 是否为空，包括对象为 null，字符串长度为0，集合类，Map 为 empty 的情况
//     *
//     * @param obj
//     * @return
//     * @author larrykoo
//     */
//    public static boolean isNullOrEmptyObject(Object obj) {
//        if (obj == null) {
//            return true;
//        }
//        if (obj instanceof CharSequence) {
//            return ((CharSequence) obj).length() == 0;
//        }
//
//        if (obj instanceof Collection) {
//            return ((Collection) obj).isEmpty();
//        }
//        if (obj instanceof Map) {
//            return ((Map) obj).isEmpty();
//        }
//        if (obj instanceof Object[]) {
//            Object[] object = (Object[]) obj;
//            if (object.length == 0) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

//    public static String trim(String str) {
//        return str == null ? null : str.trim();
//    }

//    public static String[] split(String str, char separatorChar) {
//        return splitWorker(str, separatorChar, false);
//    }

//    public static String beforeLast(String str, char separatorChar) {
//        int pos = str.lastIndexOf((int) separatorChar);
//        return pos == -1 ? "" : str.substring(0, pos);
//    }

//    public static <T> String join(Collection<T> strs, Function<T,String> format, String separator) {
//        StringBuilder sb = new StringBuilder();
//        for (T str : strs) {
//            String s = format.apply(str);
//            sb.append(s).append(separator);
//        }
//        sb.setLength(sb.length() - 1);
//        return sb.toString();
//    }

//    private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens) {
//        // Performance tuned for 2.0 (JDK1.4)
//
//        if (str == null) {
//            return null;
//        }
//        int len = str.length();
//        if (len == 0) {
//            return EMPTY_STRING_ARRAY;
//        }
//        List list = new ArrayList();
//        int i = 0, start = 0;
//        boolean match = false;
//        boolean lastMatch = false;
//        while (i < len) {
//            if (str.charAt(i) == separatorChar) {
//                if (match || preserveAllTokens) {
//                    list.add(str.substring(start, i));
//                    match = false;
//                    lastMatch = true;
//                }
//                start = ++i;
//                continue;
//            }
//            lastMatch = false;
//            match = true;
//            i++;
//        }
//        if (match || (preserveAllTokens && lastMatch)) {
//            list.add(str.substring(start, i));
//        }
//        return (String[]) list.toArray(new String[list.size()]);
//    }

//    public static String addEscape(String name, char escape) {
//        return addEscape(name, escape, escape);
//    }

//    public static String addEscape(String name, char leftEscape, char rightEscape) {
//        int index = name.indexOf('.');
//        if (index == -1) {
//            return new StringBuilder(name.length() + 2).append(leftEscape).append(name).append(rightEscape).toString();
//        } else {
//            String schema = name.substring(0, index);
//            String table = name.substring(index + 1);
//            StringBuilder sb = new StringBuilder(name.length() + 4);
//            sb.append(leftEscape).append(schema).append(rightEscape).append('.').append(leftEscape).append(table).append(rightEscape);
//            return sb.toString();
//
//        }
//    }

//    /**
//     * @param str
//     * @param maxLen 英文字符最大长度
//     * @return
//     */
//    public static List<String> getStringSegments(String str, int maxLen) {
//        List<String> segments = new ArrayList<>();
//        if (str == null || str.isEmpty()) {
//            return segments;
//        }
//        int len = 0;
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < str.length(); i++) {
//            char c = str.charAt(i);
//            if (c >= 0x4e00 && c <= 0x9fa5) { // 中文字符
//                len += 2;
//                if (len > maxLen) {
//                    segments.add(sb.toString());
//                    sb = new StringBuilder();
//                    len = 2;
//                }
//                sb.append(c);
//            } else { // 英文字符
//                len += 1;
//                if (len > maxLen) {
//                    segments.add(sb.toString());
//                    sb = new StringBuilder();
//                    len = 1;
//                }
//                sb.append(c);
//            }
//        }
//        segments.add(sb.toString());
//        return segments;
//    }

//    public static String getCharString(String str, int maxLen) {
//        if (str == null || str.isEmpty()) {
//            return "";
//        }
//        int len = 0;
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < str.length(); i++) {
//            char c = str.charAt(i);
//            if (c >= 0x4e00 && c <= 0x9fa5) { // 中文字符
//                len += 2;
//                if (len > maxLen) {
//                    break;
//                }
//                sb.append(c);
//            } else { // 英文字符
//                len += 1;
//                if (len > maxLen) {
//                    break;
//                }
//                sb.append(c);
//            }
//        }
//        return sb.toString();
//    }

//    public static boolean isChineseChar(char c) {
//        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
//        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
//                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
//                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
//                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
//                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
//                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
//            return true;
//        }
//        return false;
//    }

    // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

//    // 只能判断部分CJK字符（CJK统一汉字）
//    public static boolean isChineseByREG(String str) {
//        if (str == null) {
//            return false;
//        }
//        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
//        return pattern.matcher(str.trim()).find();
//    }
//
//    // 只能判断部分CJK字符（CJK统一汉字）
//    public static boolean isChineseByName(String str) {
//        if (str == null) {
//            return false;
//        }
//        // 大小写不同：\\p 表示包含，\\P 表示不包含
//        // \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
//        String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
//        Pattern pattern = Pattern.compile(reg);
//        return pattern.matcher(str.trim()).find();
//    }

    // 自定义方法，截取字符串并确保不截取到半个 emoji
    public static String[] safeSubstring(String input) {
        int[] codePoints = input.codePoints().toArray();

        // 构建截取后的字符串数组
        String[] substrings = new String[codePoints.length];
        StringBuilder currentSubstring = new StringBuilder();
        int currentIndex = 0;

        for (int codePoint : codePoints) {
            if (Character.isHighSurrogate((char) codePoint)) {
                // 如果遇到高代理字符，先清空当前子串，将高代理字符存入
                currentSubstring.setLength(0);
                currentSubstring.append((char) codePoint);
            } else {
                // 如果不是高代理字符，将字符存入当前子串
                currentSubstring.appendCodePoint(codePoint);
            }

            // 判断当前子串是否完整
            if (currentSubstring.codePoints().count() == 1) {
                substrings[currentIndex] = currentSubstring.toString();
                currentIndex++;
                currentSubstring.setLength(0);
            }
        }

        // 处理最后一个字符（可能是 emoji 的低代理字符）
        if (currentSubstring.length() > 0) {
            substrings[currentIndex] = currentSubstring.toString();
        }

        return substrings;
    }

    public static String safeSubstringFirst(String input) {
        int[] codePoints = input.codePoints().toArray();

        StringBuilder currentSubstring = new StringBuilder();
        if (codePoints.length == 0) {
            return EasyStringUtil.EMPTY;
        }
        int codePoint = codePoints[0];
        if (Character.isHighSurrogate((char) codePoint)) {
            // 如果遇到高代理字符，先清空当前子串，将高代理字符存入
            currentSubstring.setLength(0);
            currentSubstring.append((char) codePoint);
        } else {
            // 如果不是高代理字符，将字符存入当前子串
            currentSubstring.appendCodePoint(codePoint);
        }

        // 判断当前子串是否完整
        if (currentSubstring.codePoints().count() == 1) {
            return currentSubstring.toString();
        }

        // 处理最后一个字符（可能是 emoji 的低代理字符）
        if (currentSubstring.length() > 0) {
            return currentSubstring.toString();
        }

        return EasyStringUtil.EMPTY;
    }

    public static List<String> getStringCharSegments(String str, int maxCharLen, int otherCharLength, int chineseCharLength) {
        ArrayList<String> segments = new ArrayList<>(str.length());
        for (int i = 0; i < str.length(); i++) {
            int len = 0;
            StringBuilder segmentBuilder = new StringBuilder();
            int j = i;
            for (; j < str.length() && len < maxCharLen; j++) {
                char c = str.charAt(j);
                len += isChinese(c) ? chineseCharLength : otherCharLength;
                segmentBuilder.append(c);
            }
            segments.add(segmentBuilder.toString());
            //如果已经移动到最后一个字符,并且是刚好或者小于最大长度,那么说明后续没必要移动了
            if (j == str.length() && len <= maxCharLen) {
                break;
            }
        }
        return segments;
    }

    public static List<String> getStringSafeCharSegments(String str, int maxCharLen, int otherCharLength, int chineseCharLength) {
        ArrayList<String> segments = new ArrayList<>(str.length());
        String[] safeCharString = safeSubstring(str);
        for (int i = 0; i < safeCharString.length; i++) {
            int len = 0;
            StringBuilder segmentBuilder = new StringBuilder();
            int j = i;
            for (; j < safeCharString.length && len < maxCharLen; j++) {
                String c = safeCharString[j];
                len += (c.length() > 1 || isChinese(c)) ? chineseCharLength : otherCharLength;
                segmentBuilder.append(c);
            }
            segments.add(segmentBuilder.toString());
            //如果已经移动到最后一个字符,并且是刚好或者小于最大长度,那么说明后续没必要移动了
            if (j == safeCharString.length && len <= maxCharLen) {
                break;
            }
        }
        return segments;
    }

    public static List<String> splitBase64ByGroupSize(String str, int groupSize) {
        if (str == null || str.length() == 0 || groupSize <= 0) {
            return EasyCollectionUtil.emptyList();
        }
        List<String> groups = new ArrayList<>();
        int i = 0;
        while (i < str.length()) {
            int len = 0;
            int j = i;
            while (j < str.length() && len < groupSize) {
                char c = str.charAt(j);
                len += c < 256 ? 1 : 2;
                j++;
            }
            groups.add(str.substring(i, j));
            i = j;
        }
        return groups;
    }

    public static String leftPad(String input, int totalWidth, char paddingChar) {
        if (input.length() >= totalWidth) {
            return input;
        }
        StringBuilder sb = new StringBuilder(totalWidth);
        for (int i = 0; i < totalWidth - input.length(); i++) {
            sb.append(paddingChar);
        }
        sb.append(input);
        return sb.toString();
    }
//
//    public static void main(String[] args) {
//        System.out.println(addEscape("abc", '[', ']'));
//        System.out.println(addEscape("abc.eft", '[', ']'));
//    }
}
