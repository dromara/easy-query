package com.easy.query.core.basic.extension.formater;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.util.EasySQLUtil;

import java.util.List;

/**
 * create time 2024/3/27 11:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyBatisSQLParameterPrintFormat implements SQLParameterPrintFormat{
    @Override
    public String format(List<SQLParameter> sqlParameters) {
        return EasySQLUtil.sqlParameterToMyBatisString(sqlParameters);
    }
}
