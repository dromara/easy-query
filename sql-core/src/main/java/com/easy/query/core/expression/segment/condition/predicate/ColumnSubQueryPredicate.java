package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

import java.util.Collection;

/**
 * create time 2023/4/27 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnSubQueryPredicate implements SubQueryPredicate{
    private final SqlPredicateCompare compare;
    private final EntityExpressionBuilder sqlEntityExpression;
    private final EntityTableExpressionBuilder table;
    private final String propertyName;
    private final Queryable<?> subQueryable;

    public ColumnSubQueryPredicate(EntityTableExpressionBuilder table, String propertyName, Queryable<?> subQueryable,SqlPredicateCompare compare, EntityExpressionBuilder sqlEntityExpression) {
        this.table = table;
        this.propertyName = propertyName;
        this.subQueryable = subQueryable;
        this.compare = compare;
        this.sqlEntityExpression = sqlEntityExpression;
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
    public SqlEntitySegment cloneSqlEntitySegment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {

        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        StringBuilder sql = new StringBuilder();
        sql.append(sqlColumnSegment).append(" ").append(compare.getSql()).append(" (");
        String queryableSql = subQueryable.toSql(sqlParameterCollector);
        sql.append(queryableSql).append(") ");
        return sql.toString();
    }

    @Override
    public SqlPredicateCompare getOperator() {
        return compare;
    }

    @Override
    public Queryable<?> getSubQueryable() {
        return subQueryable;
    }
}
