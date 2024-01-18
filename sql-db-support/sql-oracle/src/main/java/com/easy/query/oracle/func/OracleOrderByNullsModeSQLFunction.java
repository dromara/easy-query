package com.easy.query.oracle.func;

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
public class OracleOrderByNullsModeSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final boolean asc;
    private final OrderByModeEnum orderByModeEnum;

    public OracleOrderByNullsModeSQLFunction(List<ColumnExpression> columnExpressions, boolean asc, OrderByModeEnum orderByModeEnum) {
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
                    return "{0} ASC NULLS FIRST";
                }
                return "{0} DESC NULLS FIRST";
            }
            case NULLS_LAST:{
                if(asc){
                    return "{0} ASC NULLS LAST";
                }
                return "{0} DESC NULLS LAST";
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
