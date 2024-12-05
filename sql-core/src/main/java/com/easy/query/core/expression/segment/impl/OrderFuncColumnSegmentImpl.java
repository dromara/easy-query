package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLKeywordEnum;
import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.OrderFuncColumnSegment;
import com.easy.query.core.expression.segment.ReverseOrderBySegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @FileName: FuncColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/19 22:17
 * @author xuejiaming
 */
public class OrderFuncColumnSegmentImpl implements OrderFuncColumnSegment, ReverseOrderBySegment {


    protected final ExpressionContext expressionContext;
    protected final ColumnFunction columnFunction;
    private final boolean asc;
    private final Column2Segment column2Segment;
    private  boolean reverse;

    public OrderFuncColumnSegmentImpl(Column2Segment column2Segment, ExpressionContext expressionContext, ColumnFunction columnFunction, boolean asc){
        this.column2Segment = column2Segment;
        this.expressionContext = expressionContext;
        this.columnFunction = columnFunction;
        this.asc = asc;
        this.reverse = false;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        String sqlColumnSegment = column2Segment.toSQL(toSQLContext);
        String funcColumn = columnFunction.getFuncColumn(sqlColumnSegment);
        StringBuilder sql = new StringBuilder().append(funcColumn);
        if(getOrderByAsc()){
            sql.append(" ").append(SQLKeywordEnum.ASC.toSQL());
        }else {
            sql.append(" ").append(SQLKeywordEnum.DESC.toSQL());
        }
        return sql.toString();
    }

    @Override
    public TableAvailable getTable() {
        return column2Segment.getTable();
    }

    @Override
    public String getPropertyName() {
        return column2Segment.getColumnMetadata().getPropertyName();
    }

    @Override
    public ColumnMetadata getColumnMetadata() {
        return column2Segment.getColumnMetadata();
    }

    @Override
    public FuncColumnSegment cloneSQLColumnSegment() {
        throw new UnsupportedOperationException();
    }
    private boolean getOrderByAsc(){
        return isReverse() != isAsc();
    }


    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public void setAlias(String alias) {
    }

    @Override
    public AggregationType getAggregationType() {
        return columnFunction.getAggregationType();
    }


    @Override
    public boolean isAsc() {
        return asc;
    }

    @Override
    public void reverseOrder() {
        this.reverse=true;
    }

    @Override
    public boolean isReverse() {
        return reverse;
    }
}
