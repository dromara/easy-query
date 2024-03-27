package com.easy.query.core.basic.extension.formater;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;

import java.util.List;

/**
 * create time 2024/3/27 10:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLParameterPrintFormat {
    String format(List<SQLParameter> sqlParameters);
}
