package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.expression.parser.core.internal.EntityTableAvailable;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.util.SqlExpressionUtil;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnPredicate implements Predicate {
    private final EntityTableAvailable table;
    private final String propertyName;
    private final SqlPredicateCompare compare;
    private final EasyQueryRuntimeContext runtimeContext;

    public ColumnPredicate(EntityTableAvailable table, String propertyName, SqlPredicateCompare compare, EasyQueryRuntimeContext runtimeContext) {
        this.table = table;
        this.propertyName = propertyName;
        this.compare = compare;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        String sqlColumnSegment = SqlExpressionUtil.getSqlOwnerColumn(runtimeContext,table,propertyName);
        return sqlColumnSegment +" "+ compare.getSql();
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
