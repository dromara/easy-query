package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.expression.parser.core.internal.EntityTableAvailable;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.util.SqlUtil;
import com.easy.query.core.util.SqlExpressionUtil;

/**
 * @FileName: FuncColumnValuePredicate.java
 * @Description: column加方法函数和对应的值比较
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class FuncColumnValuePredicate implements Predicate {
    private final EntityTableAvailable table;
    private final EasyFunc func;
    private final String propertyName;
    private final Object val;
    private final SqlPredicateCompare compare;
    private final EasyQueryRuntimeContext runtimeContext;

    public FuncColumnValuePredicate(EntityTableAvailable table, EasyFunc func, String propertyName, Object val, SqlPredicateCompare compare, EasyQueryRuntimeContext runtimeContext) {
        this.table = table;
        this.propertyName = propertyName;

        this.func = func;
        this.val = val;
        this.compare = compare;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        SqlUtil.addParameter(sqlParameterCollector,new EasyConstSQLParameter(table,propertyName,val));
        String sqlColumnSegment = SqlExpressionUtil.getSqlOwnerColumn(runtimeContext,table,propertyName);
        return func.getFuncColumn(sqlColumnSegment) +" "+ compare.getSql() + " ?";
    }

    @Override
    public EntityTableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public SqlEntitySegment cloneSqlEntitySegment() {

        throw new UnsupportedOperationException();
    }

    @Override
    public SqlPredicateCompare getOperator() {
        return compare;
    }
}
