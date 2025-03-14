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
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.List;

/**
 * create time 2024/2/28 06:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class DenseRankOverSQLFunction extends AbstractExpressionSQLFunction implements PartitionBySQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final int partitionByColumnSize;

    public DenseRankOverSQLFunction(List<ColumnExpression> columnExpressions) {
        if(EasyCollectionUtil.isEmpty(columnExpressions)){
            throw new EasyQueryInvalidOperationException("dense_rank partition by empty");
        }
        this.columnExpressions = columnExpressions;
        this.partitionByColumnSize = columnExpressions.size();
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        StringBuilder sql = new StringBuilder();

        sql.append("(DENSE_RANK() OVER (PARTITION BY ");
        for (int i = 0; i < partitionByColumnSize; i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append("{").append(i).append("}");
        }
        if (columnExpressions.size() > partitionByColumnSize) {
            sql.append(" ORDER BY ");
            for (int i = partitionByColumnSize; i < columnExpressions.size(); i++) {
                if (i > partitionByColumnSize) {
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
