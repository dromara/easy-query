package com.easy.query.core.expression.sql.builder.sort;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.visitor.EmptyTableVisitor;
import com.easy.query.core.expression.visitor.ExpressionTableVisitor;
import com.easy.query.core.expression.visitor.TableVisitor;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * create time 2025/3/9 16:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class TableSortWithRelationProcessor implements TableSortProcessor {
    private final EntityTableSQLExpression firstTableSQLExpression;
    private final TableVisitor rootTableVisitor;
    private final List<TableExpressionAndVisitor> tables;

    public TableSortWithRelationProcessor(EntityTableSQLExpression firstTableSQLExpression, TableVisitor rootTableVisitor) {
        this.firstTableSQLExpression = firstTableSQLExpression;
        this.rootTableVisitor = rootTableVisitor;
        this.tables = new ArrayList<>();
    }

    @Override
    public void appendTable(EntityTableSQLExpression entityTableSQLExpression) {
        if (entityTableSQLExpression == firstTableSQLExpression) {
            tables.add(new TableExpressionAndVisitor(entityTableSQLExpression, EmptyTableVisitor.DEFAULT));
        } else {
            ExpressionTableVisitor expressionTableVisitor = new ExpressionTableVisitor();
            EasySQLSegmentUtil.tableVisit(entityTableSQLExpression.getOn(), expressionTableVisitor);
            rootTableVisitor.addAll(expressionTableVisitor);
            tables.add(new TableExpressionAndVisitor(entityTableSQLExpression, expressionTableVisitor));
        }

    }

    @Override
    public List<EntityTableSQLExpression> getTables() {
        return tables.stream().sorted((a, b) -> getTableExpressionCompare(a, b)).map(o -> o.tableSQLExpression).collect(Collectors.toList());
    }

    private int getTableExpressionCompare(TableExpressionAndVisitor a, TableExpressionAndVisitor b) {
        if (a.tableSQLExpression == firstTableSQLExpression) {
            return -1;
        }
        if (b.tableSQLExpression == firstTableSQLExpression) {
            return 1;
        }
        TableAvailable prevTable = a.tableSQLExpression.getEntityTable();
        TableAvailable nextTable = b.tableSQLExpression.getEntityTable();
        if (a.tableVisitor.containsTable(nextTable)) {
            return 1;
        }
        if (b.tableVisitor.containsTable(prevTable)) {
            return -1;
        }
        return 0;
    }

    public static class TableExpressionAndVisitor {
        public EntityTableSQLExpression tableSQLExpression;
        public TableVisitor tableVisitor;

        public TableExpressionAndVisitor(EntityTableSQLExpression tableSQLExpression, TableVisitor tableVisitor) {
            this.tableSQLExpression = tableSQLExpression;
            this.tableVisitor = tableVisitor;
        }
    }
}
