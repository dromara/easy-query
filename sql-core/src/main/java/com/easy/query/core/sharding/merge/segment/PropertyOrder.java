package com.easy.query.core.sharding.merge.segment;

import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;

/**
 * create time 2023/4/27 12:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PropertyOrder {
    EasyTableSqlExpression getTable();

    String propertyName();

    Class<?> propertyType();

    int columnIndex();

    boolean asc();
}
