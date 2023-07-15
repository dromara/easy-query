package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

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
    private final QueryRuntimeContext runtimeContext;

    public FuncColumnValuePredicate(TableAvailable table, ColumnFunction func, String propertyName, Object val, SQLPredicateCompare compare, QueryRuntimeContext runtimeContext) {
        this.table = table;
        this.propertyName = propertyName;

        this.func = func;
        this.val = val;
        this.compare = compare;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLUtil.addParameter(toSQLContext,new EasyConstSQLParameter(table,propertyName,val));
        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,table,propertyName,toSQLContext);
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
    public SQLEntitySegment cloneSQLColumnSegment() {

        throw new UnsupportedOperationException();
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }
}
