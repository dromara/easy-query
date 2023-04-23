package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.util.SQLUtil;

/**
 * @FileName: FuncColumnValuePredicate.java
 * @Description: column加方法函数和对应的值比较
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class FuncColumnValuePredicate implements Predicate {
    private final EntityTableExpressionBuilder table;
    private final EasyFunc func;
    private final String propertyName;
    private final Object val;
    private final SqlPredicateCompare compare;
    private final EntityExpressionBuilder sqlEntityExpression;

    public FuncColumnValuePredicate(EntityTableExpressionBuilder table, EasyFunc func, String propertyName, Object val, SqlPredicateCompare compare, EntityExpressionBuilder sqlEntityExpression) {
        this.table = table;
        this.propertyName = propertyName;

        this.func = func;
        this.val = val;
        this.compare = compare;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        SQLUtil.addParameter(sqlParameterCollector,new EasyConstSQLParameter(table,propertyName,val));
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        return func.getFuncColumn(sqlColumnSegment) +" "+ compare.getSql() + " ?";
    }

    @Override
    public EntityTableExpressionBuilder getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public SqlPredicateCompare getOperator() {
        return compare;
    }
}
