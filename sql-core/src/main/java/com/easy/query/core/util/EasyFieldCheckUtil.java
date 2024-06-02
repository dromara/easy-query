package com.easy.query.core.util;

/**
 * create time 2024/6/1 22:20
 * 文件说明
 *
 * @author xuejiaming
 */

import com.easy.query.core.exception.EasyQueryInvalidFieldCheckException;

import java.util.regex.Pattern;

/**
 * 参考代码
 * 代码参考 <a href="https://github.com/DotNetNext/SqlSugar">SqlSugar</a>
 * https://github.com/DotNetNext/SqlSugar/blob/master/Src/Asp.NetCore2/SqlSugar/Utilities/DbExtensions.cs
 */
public class EasyFieldCheckUtil {

    public static String toCheckField(String value) {
        if (StaticConfig.Check_FieldFunc != null) {
            return StaticConfig.Check_FieldFunc.apply(value);
        }

        if (value != null) {
            if (containsAny(value, ";", "--")) {
                throw new EasyQueryInvalidFieldCheckException(String.format("%s format error", value));
            } else if (containsAny(value, "//") && (value.length() - value.replace("/", "").length()) >= 4) {
                throw new EasyQueryInvalidFieldCheckException(String.format("%s format error", value));
            } else if (containsAny(value, "'") && (value.length() - value.replace("'", "").length()) % 2 != 0) {
                throw new EasyQueryInvalidFieldCheckException(String.format("%s format error", value));
            } else if (isUpdateSql(value, "/", "/")) {
                throw new EasyQueryInvalidFieldCheckException(String.format("%s format error  %s不能存在  /+【update drop 等】+/", value, value));
            } else if (isUpdateSql(value, "/", " ")) {
                throw new EasyQueryInvalidFieldCheckException(String.format("%s format error  %s不能存在  /+【update drop 等】+空格", value, value));
            } else if (isUpdateSql(value, " ", "/")) {
                throw new EasyQueryInvalidFieldCheckException(String.format("%s format error  %s不能存在  空格+【update drop 等】+/", value, value));
            } else if (value.toLowerCase().contains(" update ") ||
                    value.toLowerCase().contains(" delete ") ||
                    value.toLowerCase().contains(" drop ") ||
                    value.toLowerCase().contains(" alert ") ||
                    value.toLowerCase().contains(" create ") ||
                    value.toLowerCase().contains(" insert ")) {
                throw new EasyQueryInvalidFieldCheckException(String.format("%s format error  %s不能存在  空格+【update drop 等】+空格", value, value));
            }
        }
        return value;
    }

    private static boolean isUpdateSql(String value, String left, String right) {
        value = value.toLowerCase();
        return value.contains(left + "update" + right) ||
                value.contains(left + "delete" + right) ||
                value.contains(left + "drop" + right) ||
                value.contains(left + "alert" + right) ||
                value.contains(left + "create" + right) ||
                value.contains(left + "insert" + right);
    }

    private static boolean containsAny(String value, String... searchStrings) {
        return EasyArrayUtil.any(searchStrings,value::contains);
    }

    public static boolean containsChinese(String input) {
        String pattern = "[\\u4e00-\\u9fa5]";
        return Pattern.compile(pattern).matcher(input).find();
    }
    public static String toLower(String value, boolean isAutoToLower) {
        if (value == null) return null;
        return isAutoToLower ? value.toLowerCase() : value;
    }

    public static String toUpper(String value, boolean isAutoToUpper) {
        if (value == null) return null;
        return isAutoToUpper ? value.toUpperCase() : value;
    }
}
