package org.easy.query.core.expression.segment.condition.predicate;

import org.easy.query.core.abstraction.sql.enums.IEasyFunc;
import org.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import org.easy.query.core.enums.SqlPredicateCompare;
import org.easy.query.core.query.SqlEntityExpression;
import org.easy.query.core.query.SqlEntityQueryExpression;
import org.easy.query.core.query.SqlEntityTableExpression;

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
    private final SqlPredicateCompare compare;
    private final SqlEntityExpression sqlEntityExpression;

    public FuncColumnValuePredicate0(SqlEntityTableExpression table, IEasyFunc func, String propertyName, Object val, SqlPredicateCompare compare, SqlEntityExpression sqlEntityExpression) {
        this.table = table;
        this.propertyName = propertyName;

        this.func = func;
        this.compare = compare;
        this.sqlEntityExpression = sqlEntityExpression;
        sqlEntityExpression.addParameter(new ConstSQLParameter(table,propertyName,val));
    }

    @Override
    public String toSql() {
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        return func.getFuncColumn(sqlColumnSegment) +" "+ compare.getSql() + " ?";
    }
}
