package org.easy.query.core.expression.segment;

import org.easy.query.core.enums.SqlKeywordEnum;
import org.easy.query.core.expression.context.SelectContext;
import org.easy.query.core.query.builder.SqlTableInfo;

/**
 * @FileName: OrderColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 22:18
 * @Created by xuejiaming
 */
public class OrderColumnSegment extends ColumnSegment{
    public boolean isAsc() {
        return asc;
    }

    private final boolean asc;

    public OrderColumnSegment(SqlTableInfo table, String propertyName, SelectContext selectContext, boolean asc) {
        super(table,propertyName, selectContext);
        this.asc = asc;
    }

    @Override
    public String toSql() {

        String sqlColumnSegment = sqlContext.getSqlColumnSegment(table,propertyName);
        StringBuilder sql = new StringBuilder().append(sqlColumnSegment);
        if(asc){
            sql.append(" ").append(SqlKeywordEnum.ASC.toSql());
        }else {
            sql.append(" ").append(SqlKeywordEnum.DESC.toSql());
        }
        return sql.toString();
    }
}
