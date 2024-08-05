package com.easy.query.core.func.def.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.builder.impl.OrderSelectorImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.impl.DefaultSQLSegment;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.impl.ColumSQLExpressionImpl;
import com.easy.query.core.func.column.impl.ColumnFunctionExpressionImpl;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.func.def.PartitionBySQLFunction;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2024/2/28 06:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class RowNumberSQLFunction extends AbstractExpressionSQLFunction implements PartitionBySQLFunction {
    private final List<ColumnExpression> columnExpressions;

    public RowNumberSQLFunction(List<ColumnExpression> columnExpressions) {
        this.columnExpressions = columnExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        StringBuilder sql = new StringBuilder();

        sql.append("(ROW_NUMBER() OVER (PARTITION BY {0}");
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
