package com.easy.query.core.util;

import com.easy.query.core.exception.EasyQueryInvalidFieldCheckException;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * 只允许数字字母下划线
 */
public class EasyFieldCheckUtil {

    private static final Pattern SAFE_PATTERN = Pattern.compile("^[A-Za-z0-9_]+$");

//    private static boolean isUnSafeChar(char ch) {
//        for (char c : UN_SAFE_CHARS) {
//            if (c == ch) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static String toCheckField(String column) {
        if (EasyStringUtil.isBlank(column)) {
            throw new EasyQueryInvalidFieldCheckException("column name must not be empty");
        }
        if (!SAFE_PATTERN.matcher(column).matches()) {
            throw new EasyQueryInvalidFieldCheckException("column name only allows letters (A-Z, a-z), digits (0-9), and underscore (_), invalid:" + column);
        }
        return column;
    }
}
