package org.easy.query.core.basic.sql.segment.segment;

import org.easy.query.core.enums.SqlKeywordEnum;
import org.easy.query.core.basic.api.context.SelectContext;

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

    public OrderColumnSegment(int index, String columnName,String propertyName, SelectContext selectContext, boolean asc) {
        super(index, columnName,propertyName, selectContext);
        this.asc = asc;
    }

    @Override
    public String getSql() {

        String sqlColumnSegment = getSqlContext().getSqlColumnSegment(getIndex(),getColumnName());
        StringBuilder sql = new StringBuilder().append(sqlColumnSegment);
        if(asc){
            sql.append(" ").append(SqlKeywordEnum.ASC.getSql());
        }else {
            sql.append(" ").append(SqlKeywordEnum.DESC.getSql());
        }
        return sql.toString();
    }
}
