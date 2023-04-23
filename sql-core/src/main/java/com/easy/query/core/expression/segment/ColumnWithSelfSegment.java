package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.SQLUtil;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnWithSelfSegment implements SqlEntitySegment {
    private static final String INCREMENT="+?";
    private static final String DECREMENT="-?";
    private final EntityTableExpressionBuilder table;
    private final String propertyName;
    private final Object val;
    private final SqlPredicateCompare compare;
    private final EntityExpressionBuilder sqlEntityExpression;
    private final String selfLink;

    public ColumnWithSelfSegment(boolean increment, EntityTableExpressionBuilder table, String propertyName, Object val, SqlPredicateCompare compare, EntityExpressionBuilder sqlEntityExpression) {
        this.selfLink=increment?INCREMENT:DECREMENT;
        this.table = table;
        this.propertyName = propertyName;
        this.val = val;
        this.compare = compare;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        SQLUtil.addParameter(sqlParameterCollector,new EasyConstSQLParameter(table,propertyName,val));
        String sqlColumnSegment1 = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        return sqlColumnSegment1 +" "+ compare.getSql() + " "+sqlColumnSegment1+selfLink;
    }

    @Override
    public EntityTableExpressionBuilder getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }
}
