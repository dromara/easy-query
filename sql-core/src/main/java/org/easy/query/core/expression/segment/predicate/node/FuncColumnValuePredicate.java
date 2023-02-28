package org.easy.query.core.expression.segment.predicate.node;

import org.easy.query.core.abstraction.sql.enums.IEasyFunc;
import org.easy.query.core.basic.api.context.SqlContext;
import org.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import org.easy.query.core.enums.SqlPredicateCompare;
import org.easy.query.core.query.builder.SqlTableInfo;

/**
 * @FileName: FuncColumnValuePredicate.java
 * @Description: column加方法函数和对应的值比较
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class FuncColumnValuePredicate implements Predicate {
    private final SqlTableInfo table;
    private final IEasyFunc func;
    private final String propertyName;
    private final Object val;
    private final SqlPredicateCompare compare;
    private final SqlContext sqlContext;

    public FuncColumnValuePredicate(SqlTableInfo table, IEasyFunc func, String propertyName, Object val, SqlPredicateCompare compare, SqlContext sqlContext) {
        this.table = table;
        this.propertyName = propertyName;

        this.func = func;
        this.val = val;
        this.compare = compare;
        this.sqlContext = sqlContext;
    }

    @Override
    public String getSql() {
        sqlContext.addParameter(new ConstSQLParameter(table,propertyName,val));
        String sqlColumnSegment = sqlContext.getSqlColumnSegment(table,propertyName);
        return func.getFuncColumn(sqlColumnSegment) +" "+ compare.getSql() + " ?";
    }
}
