package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.enums.IEasyFunc;
import com.easy.query.core.query.SqlEntityExpression;
import com.easy.query.core.query.SqlEntityTableExpression;
import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.query.*;

/**
 * @FileName: FuncColumnValuePredicate.java
 * @Description: column加方法函数和对应的值比较
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class FuncColumnValuePredicate0 implements Predicate {
    private final SqlEntityTableExpression table;
    private final IEasyFunc func;
    private final String propertyName;
    private final Object val;
    private final SqlPredicateCompare compare;
    private final SqlEntityExpression sqlEntityExpression;

    public FuncColumnValuePredicate0(SqlEntityTableExpression table, IEasyFunc func, String propertyName, Object val, SqlPredicateCompare compare, SqlEntityExpression sqlEntityExpression) {
        this.table = table;
        this.propertyName = propertyName;

        this.func = func;
        this.val = val;
        this.compare = compare;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public String toSql() {
        sqlEntityExpression.addParameter(new ConstSQLParameter(table,propertyName,val));
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        return func.getFuncColumn(sqlColumnSegment) +" "+ compare.getSql() + " ?";
    }

    @Override
    public SqlEntityTableExpression getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }
}
