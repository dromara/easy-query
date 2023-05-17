package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.util.SQLUtil;
import com.easy.query.core.util.SQLExpressionUtil;

/**
 * @FileName: FuncColumnValuePredicate.java
 * @Description: column加方法函数和对应的值比较
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class FuncColumnValuePredicate implements Predicate {
    private final TableAvailable table;
    private final ColumnFunction func;
    private final String propertyName;
    private final Object val;
    private final SQLPredicateCompare compare;
    private final EasyQueryRuntimeContext runtimeContext;

    public FuncColumnValuePredicate(TableAvailable table, ColumnFunction func, String propertyName, Object val, SQLPredicateCompare compare, EasyQueryRuntimeContext runtimeContext) {
        this.table = table;
        this.propertyName = propertyName;

        this.func = func;
        this.val = val;
        this.compare = compare;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {
        SQLUtil.addParameter(sqlParameterCollector,new EasyConstSQLParameter(table,propertyName,val));
        String sqlColumnSegment = SQLExpressionUtil.getSQLOwnerColumn(runtimeContext,table,propertyName);
        return func.getFuncColumn(sqlColumnSegment) +" "+ compare.getSQL() + " ?";
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public SQLEntitySegment cloneSQLEntitySegment() {

        throw new UnsupportedOperationException();
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }
}
