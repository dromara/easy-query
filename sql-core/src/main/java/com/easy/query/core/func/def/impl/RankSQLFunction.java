package com.easy.query.core.func.def.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.impl.ColumnFunctionExpressionImpl;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.func.def.PartitionBySQLFunction;

import java.util.List;

/**
 * create time 2024/2/28 06:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class RankSQLFunction extends AbstractExpressionSQLFunction implements PartitionBySQLFunction {
    private final List<ColumnExpression> columnExpressions;

    public RankSQLFunction(List<ColumnExpression> columnExpressions) {
        this.columnExpressions = columnExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        StringBuilder sql = new StringBuilder();

        sql.append("(RANK() OVER (PARTITION BY {0}");
        if (columnExpressions.size() > 1) {
            sql.append(" ORDER BY ");
            for (int i = 1; i < columnExpressions.size(); i++) {
                if(i>1){
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
}
