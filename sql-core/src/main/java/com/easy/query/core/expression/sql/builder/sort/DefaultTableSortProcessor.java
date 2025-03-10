package com.easy.query.core.expression.sql.builder.sort;

import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.visitor.TableVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2025/3/9 16:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultTableSortProcessor implements TableSortProcessor {
    private final List<EntityTableSQLExpression> tables=new ArrayList<>();
    @Override
    public void appendTable(EntityTableSQLExpression entityTableSQLExpression) {
        tables.add(entityTableSQLExpression);
    }

    @Override
    public List<EntityTableSQLExpression> getTables() {
        return tables;
    }
}
