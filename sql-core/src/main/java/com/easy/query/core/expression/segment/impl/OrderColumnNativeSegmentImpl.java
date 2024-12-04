package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLKeywordEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.segment.ReverseOrderBySegment;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @author xuejiaming
 * @FileName: OrderColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 22:18
 */
public class OrderColumnNativeSegmentImpl extends ColumnNativeSegmentImpl implements OrderBySegment, ReverseOrderBySegment {

    private final boolean asc;
    private boolean reverse;

    public OrderColumnNativeSegmentImpl(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, SQLNativeSegment sqlNativeSegment, boolean asc) {
        super(table, columnMetadata, expressionContext,sqlNativeSegment,null);
        this.asc = asc;
        this.reverse = false;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {

        String columnSQLSegment = super.toSQL(toSQLContext);
        StringBuilder sql = new StringBuilder().append(columnSQLSegment);
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
