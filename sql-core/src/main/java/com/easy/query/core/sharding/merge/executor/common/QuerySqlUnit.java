package com.easy.query.core.sharding.merge.executor.common;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;

import java.util.List;

/**
 * create time 2023/4/14 16:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class QuerySqlUnit extends BaseSqlUnit{
    private final Class<?> clazz;
    private final List<SQLParameter> parameters;

    public QuerySqlUnit(String sql,Class<?> clazz, List<SQLParameter> parameters) {
        super(sql);
        this.clazz = clazz;
        this.parameters = parameters;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public List<SQLParameter> getParameters() {
        return parameters;
    }
}
