package com.easy.query.core.expression.segment;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlKeywordEnum;
import com.easy.query.core.expression.parser.core.internal.EntityTableAvailable;
import com.easy.query.core.util.SqlExpressionUtil;

/**
 * @FileName: OrderColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 22:18
 * @author xuejiaming
 */
public class OrderColumnSegmentImpl extends ColumnSegmentImpl implements OrderByColumnSegment {
    @Override
    public GroupByColumnSegment createGroupByColumnSegment() {
        return new GroupColumnSegmentImpl(table,propertyName,runtimeContext);
    }

    @Override
    public boolean isAsc() {
        return asc;
    }

    private final boolean asc;

    public OrderColumnSegmentImpl(EntityTableAvailable table, String propertyName, EasyQueryRuntimeContext runtimeContext, boolean asc) {
        super(table,propertyName, runtimeContext);
        this.asc = asc;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {

        String sqlColumnSegment = SqlExpressionUtil.getSqlOwnerColumn(runtimeContext,table,propertyName);
        StringBuilder sql = new StringBuilder().append(sqlColumnSegment);
        if(asc){
            sql.append(" ").append(SqlKeywordEnum.ASC.toSql());
        }else {
            sql.append(" ").append(SqlKeywordEnum.DESC.toSql());
        }
        return sql.toString();
    }
}
