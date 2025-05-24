package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLKeywordEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.segment.ReverseOrderBySegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @author xuejiaming
 */
public class OrderColumnSegmentImpl extends ColumnSegmentImpl implements OrderBySegment, ReverseOrderBySegment {

    private final boolean asc;
    private boolean reverse;

    public OrderColumnSegmentImpl(Column2Segment column2Segment, boolean asc) {
        super(column2Segment,null);
        this.asc = asc;
        this.reverse = false;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {

        String sqlColumnSegment = column2Segment.toSQL(toSQLContext);
        StringBuilder sql = new StringBuilder().append(sqlColumnSegment);
        if (getOrderByAsc()) {
            sql.append(" ").append(SQLKeywordEnum.ASC.toSQL());
        } else {
            sql.append(" ").append(SQLKeywordEnum.DESC.toSQL());
        }
        return sql.toString();
    }

    @Override
    public boolean isAsc() {
        return asc;
    }
    private boolean getOrderByAsc(){
        return isReverse() != isAsc();
    }


    @Override
    public void reverseOrder() {
        reverse = true;
    }

    @Override
    public boolean isReverse() {
        return reverse;
    }
}
