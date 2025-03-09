package com.easy.query.core.expression.sql.builder.sort;

import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;

import java.util.List;

/**
 * create time 2025/3/9 16:05
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableSortProcessor {

    void appendTable(EntityTableSQLExpression entityTableSQLExpression);
    List<EntityTableSQLExpression> getTables();
}
