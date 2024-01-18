package com.easy.query.core.func.def.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.func.def.enums.OrderByModeEnum;

import java.util.List;
import java.util.Objects;

/**
 * create time 2023/10/28 15:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class OrderByNullsModeSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final boolean asc;
    private final OrderByModeEnum orderByModeEnum;

    public OrderByNullsModeSQLFunction(List<ColumnExpression> columnExpressions, boolean asc, OrderByModeEnum orderByModeEnum) {
        this.columnExpressions = columnExpressions;
        this.asc = asc;
        Objects.requireNonNull(orderByModeEnum,"orderByModeEnum is null");
        this.orderByModeEnum = orderByModeEnum;
    }


    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(columnExpressions.size()!=1){
            throw new IllegalArgumentException("order by nulls mode arguments != 1");
        }
        switch (orderByModeEnum){
            case NULLS_FIRST:{
                if(asc){
                    return "CASE WHEN {0} IS NULL THEN 0 ELSE 1 END ASC,{0} ASC";
                }
                return "CASE WHEN {0} IS NULL THEN 0 ELSE 1 END ASC,{0} DESC";
            }
            case NULLS_LAST:{
                if(asc){
                    return "CASE WHEN {0} IS NULL THEN 1 ELSE 0 END ASC,{0} ASC";
                }
                return "CASE WHEN {0} IS NULL THEN 1 ELSE 0 END ASC,{0} DESC";
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public int paramMarks() {
        return columnExpressions.size();
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }
}
