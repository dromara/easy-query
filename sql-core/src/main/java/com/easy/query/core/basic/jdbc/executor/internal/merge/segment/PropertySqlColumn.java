package com.easy.query.core.basic.jdbc.executor.internal.merge.segment;

import com.easy.query.core.expression.sql.expression.EasyTableSQLExpression;

/**
 * create time 2023/4/28 16:27
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PropertySqlColumn {

    EasyTableSQLExpression getTable();

    String propertyName();


    int columnIndex();

}
