package com.easy.query.core.expression.segment;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLKeywordEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.EasySQLExpressionUtil;

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

    public OrderColumnSegmentImpl(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, boolean asc) {
        super(table,propertyName, runtimeContext);
        this.asc = asc;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {

        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumn(runtimeContext,table,propertyName,toSQLContext);
        StringBuilder sql = new StringBuilder().append(sqlColumnSegment);
        if(asc){
            sql.append(" ").append(SQLKeywordEnum.ASC.toSQL());
        }else {
            sql.append(" ").append(SQLKeywordEnum.DESC.toSQL());
        }
        return sql.toString();
    }
}
