package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLKeywordEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @author xuejiaming
 * @FileName: OrderColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 22:18
 */
public class OrderColumnSegmentImpl extends ColumnSegmentImpl implements OrderBySegment {

    private final boolean asc;

    public OrderColumnSegmentImpl(TableAvailable table, ColumnMetadata columnMetadata, QueryRuntimeContext runtimeContext, boolean asc) {
        super(table, columnMetadata, runtimeContext);
        this.asc = asc;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {

        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnMetadata(runtimeContext, table, columnMetadata, toSQLContext);
        StringBuilder sql = new StringBuilder().append(sqlColumnSegment);
        if (asc) {
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
    @Override
    protected boolean ignoreAlias() {
        return true;
    }
}
