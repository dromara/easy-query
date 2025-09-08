package com.easy.query.core.util;

import com.easy.query.core.common.DefaultMapColumnNameChecker;
import com.easy.query.core.common.MapColumnNameChecker;
import com.easy.query.core.exception.EasyQueryInvalidFieldCheckException;

import java.util.regex.Pattern;

/**
 * 只允许数字字母下划线
 */
public class EasyFieldCheckUtil {

    private static final Pattern SAFE_PATTERN = Pattern.compile("^[A-Za-z0-9_\\u4e00-\\u9fa5]+$");


    /**
     * 替换框架map name接口检查
     * {@link MapColumnNameChecker} -> {@link DefaultMapColumnNameChecker}
     * @param column
     * @return
     */
    public static String toCheckField(String column) {
        if (EasyStringUtil.isBlank(column)) {
            throw new EasyQueryInvalidFieldCheckException("column name must not be empty");
        }
        if (!SAFE_PATTERN.matcher(column).matches()) {
            throw new EasyQueryInvalidFieldCheckException("column name only allows letters (A-Z, a-z), digits (0-9), and underscore (_), invalid:[" + column+"]");
        }
        return column;
    }
}
