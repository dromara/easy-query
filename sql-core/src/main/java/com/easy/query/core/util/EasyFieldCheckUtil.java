package com.easy.query.core.util;

/**
 * create time 2024/6/1 22:20
 * 文件说明
 *
 * @author xuejiaming
 */

import com.easy.query.core.exception.EasyQueryInvalidFieldCheckException;

import java.util.function.Function;

/**
 * 参考了mybatis-flex的的检查代码
 * <a href="https://gitee.com/mybatis-flex/mybatis-flex">mybatis-flex</a>
 */
public class EasyFieldCheckUtil {

    private static final char[] UN_SAFE_CHARS = "'`\"<>&+=#-;".toCharArray();
    public static Function<String, String> checkFieldFunction = EasyFieldCheckUtil::toCheckField0;

    private static boolean isUnSafeChar(char ch) {
        for (char c : UN_SAFE_CHARS) {
            if (c == ch) {
                return true;
            }
        }
        return false;
    }

    public static String toCheckField(String column) {
        return checkFieldFunction.apply(column);
    }


    public static String toCheckField0(String column) {

        if (EasyStringUtil.isBlank(column)) {
            throw new EasyQueryInvalidFieldCheckException("column name must not be empty");
        }

        int strLen = column.length();
        for (int i = 0; i < strLen; ++i) {
            char ch = column.charAt(i);
            if (Character.isWhitespace(ch)) {
                throw new EasyQueryInvalidFieldCheckException("column name must not has space char.");
            }
            if (isUnSafeChar(ch)) {
                throw new EasyQueryInvalidFieldCheckException("column name has unsafe char: [" + ch + "].");
            }
        }
        return column;
    }
}
