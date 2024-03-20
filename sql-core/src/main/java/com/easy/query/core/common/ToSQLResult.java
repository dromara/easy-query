package com.easy.query.core.common;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;

/**
 * create time 2024/3/20 16:18
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ToSQLResult {
    private final String sql;
    private final ToSQLContext sqlContext;

    public ToSQLResult(String sql, ToSQLContext sqlContext){

        this.sql = sql;
        this.sqlContext = sqlContext;
    }

    public String getSql() {
        return sql;
    }

    public ToSQLContext getSqlContext() {
        return sqlContext;
    }
}
