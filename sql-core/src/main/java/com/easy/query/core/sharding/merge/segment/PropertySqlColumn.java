package com.easy.query.core.sharding.merge.segment;

import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;

/**
 * create time 2023/4/28 16:27
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PropertySqlColumn {

    EasyTableSqlExpression getTable();

    String propertyName();


    int columnIndex();

}
