package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLKeywordEnum;
import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.OrderFuncColumnSegment;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @FileName: FuncColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/19 22:17
 * @author xuejiaming
 */
public class OrderFuncColumnSegmentImpl implements OrderFuncColumnSegment {


    protected final TableAvailable table;
    protected final String propertyName;
    protected final QueryRuntimeContext runtimeContext;
    protected final ColumnFunction columnFunction;
    private final boolean asc;

    public OrderFuncColumnSegmentImpl(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, ColumnFunction columnFunction, boolean asc){
        this.table = table;
        this.propertyName = propertyName;
        this.runtimeContext = runtimeContext;
        this.columnFunction = columnFunction;
        this.asc = asc;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,table,propertyName,toSQLContext);
        String funcColumn = columnFunction.getFuncColumn(sqlColumnSegment);
        StringBuilder sql = new StringBuilder().append(funcColumn);
        if(asc){
            sql.append(" ").append(SQLKeywordEnum.ASC.toSQL());
        }else {
            sql.append(" ").append(SQLKeywordEnum.DESC.toSQL());
        }
        return sql.toString();
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public FuncColumnSegment cloneSQLEntitySegment() {
        throw new UnsupportedOperationException();
    }


    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public AggregationType getAggregationType() {
        return columnFunction.getAggregationType();
    }


    @Override
    public boolean isAsc() {
        return asc;
    }
}
