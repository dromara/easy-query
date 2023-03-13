package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.query.SqlEntityExpression;
import com.easy.query.core.query.SqlEntityTableExpression;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnWithSelfPredicate implements Predicate, SqlEntitySegment {
    private static final String INCREMENT="+?";
    private static final String DECREMENT="-?";
    private final SqlEntityTableExpression table;
    private final String propertyName;
    private final Object val;
    private final SqlPredicateCompare compare;
    private final SqlEntityExpression sqlEntityExpression;
    private final String selfLink;

    public ColumnWithSelfPredicate(boolean increment, SqlEntityTableExpression table, String propertyName,Object val, SqlPredicateCompare compare, SqlEntityExpression sqlEntityExpression) {
        this.selfLink=increment?INCREMENT:DECREMENT;
        this.table = table;
        this.propertyName = propertyName;
        this.val = val;
        this.compare = compare;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public String toSql() {
        sqlEntityExpression.addParameter(new ConstSQLParameter(table,propertyName,val));
        String sqlColumnSegment1 = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        return sqlColumnSegment1 +" "+ compare.getSql() + " "+sqlColumnSegment1+selfLink;
    }

    @Override
    public SqlEntityTableExpression getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public String getAlias() {
        return null;
    }
}
