package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlKeywordEnum;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

/**
 * @FileName: OrderColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 22:18
 * @author xuejiaming
 */
public class OrderColumnSegment extends ColumnSegment{
    public boolean isAsc() {
        return asc;
    }

    private final boolean asc;

    public OrderColumnSegment(EntityTableExpressionBuilder table, String propertyName, EntityQueryExpressionBuilder sqlEntityExpression, boolean asc) {
        super(table,propertyName, sqlEntityExpression);
        this.asc = asc;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {

        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        StringBuilder sql = new StringBuilder().append(sqlColumnSegment);
        if(asc){
            sql.append(" ").append(SqlKeywordEnum.ASC.toSql());
        }else {
            sql.append(" ").append(SqlKeywordEnum.DESC.toSql());
        }
        return sql.toString();
    }
}
