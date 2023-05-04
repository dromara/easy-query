package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.expression.parser.core.internal.EntityTableAvailable;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.util.SqlExpressionUtil;

/**
 * create time 2023/4/27 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnSubQueryPredicate implements SubQueryPredicate{
    private final SqlPredicateCompare compare;
    private final EasyQueryRuntimeContext runtimeContext;
    private final EntityTableAvailable table;
    private final String propertyName;
    private final Queryable<?> subQueryable;

    public ColumnSubQueryPredicate(EntityTableAvailable table, String propertyName, Queryable<?> subQueryable, SqlPredicateCompare compare, EasyQueryRuntimeContext runtimeContext) {
        this.table = table;
        this.propertyName = propertyName;
        this.subQueryable = subQueryable;
        this.compare = compare;
        this.runtimeContext = runtimeContext;
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
    public String toSql(SqlParameterCollector sqlParameterCollector) {

        String sqlColumnSegment = SqlExpressionUtil.getSqlOwnerColumn(runtimeContext,table,propertyName);
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
