package com.easy.query.core.func.def.impl;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.impl.CaseWhenSQLSegment;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.impl.ColumSQLExpressionImpl;
import com.easy.query.core.func.column.impl.ColumnFunctionExpressionImpl;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.func.def.PartitionBySQLFunction;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.List;

/**
 * create time 2024/2/28 06:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class MaxOverSQLFunction extends AbstractExpressionSQLFunction implements PartitionBySQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final int partitionByColumnSizePlusOne;

    public MaxOverSQLFunction(List<ColumnExpression> columnExpressions) {
        this.columnExpressions = columnExpressions;
        this.partitionByColumnSizePlusOne = columnExpressions.size();
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if (columnExpressions.size() < 2) {
            throw new EasyQueryInvalidOperationException("max over columnExpressions < 2");
        }
        StringBuilder sql = new StringBuilder();

        sql.append("(MAX({0}) OVER (PARTITION BY ");
        for (int i = 1; i < partitionByColumnSizePlusOne; i++) {
            if (i > 1) {
                sql.append(", ");
            }
            sql.append("{").append(i).append("}");
        }
        if (columnExpressions.size() > partitionByColumnSizePlusOne) {
            sql.append(" ORDER BY ");
            for (int i = partitionByColumnSizePlusOne; i < columnExpressions.size(); i++) {
                if (i > partitionByColumnSizePlusOne) {
                    sql.append(", ");
                }
                sql.append("{").append(i).append("}");
            }
        }
        sql.append("))");
        return sql.toString();
    }

    @Override
    public int paramMarks() {
        return columnExpressions.size();
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }

    @Override
    public PartitionBySQLFunction addOrder(SQLFunction sqlFunction) {
        ColumnFunctionExpressionImpl columnFunctionExpression = new ColumnFunctionExpressionImpl(null, sqlFunction);
        columnExpressions.add(columnFunctionExpression);
        return this;
    }
    @Override
    public PartitionBySQLFunction addOrder(SQLBuilderSegment sqlSegment) {
        ColumSQLExpressionImpl columSQLExpression = new ColumSQLExpressionImpl(new CaseWhenSQLSegment(toSQLContext -> sqlSegment.toSQL(toSQLContext), visitor -> EasySQLSegmentUtil.tableVisit(sqlSegment, visitor)));
        columnExpressions.add(columSQLExpression);
        return this;
    }
}
