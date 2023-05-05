package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlKeywordEnum;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.util.SqlExpressionUtil;

/**
 * create time 2023/4/27 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnExistsSubQueryPredicate implements SubQueryPredicate{
    private final SqlKeywordEnum sqlKeyword;
    private final EasyQueryRuntimeContext runtimeContext;
    private final TableAvailable table;
    private final Queryable<?> subQueryable;

    public ColumnExistsSubQueryPredicate(TableAvailable table, Queryable<?> subQueryable, SqlKeywordEnum sqlKeyword, EasyQueryRuntimeContext runtimeContext) {
        this.table = table;
        this.subQueryable = subQueryable;
        this.sqlKeyword = sqlKeyword;
        this.runtimeContext = runtimeContext;
    }
    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlEntitySegment cloneSqlEntitySegment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {

        StringBuilder sql = new StringBuilder();
        sql.append(sqlKeyword.toSql()).append(" (");
        String queryableSql = subQueryable.toSql(sqlParameterCollector);
        sql.append(queryableSql).append(") ");
        return sql.toString();
    }

    @Override
    public SqlPredicateCompare getOperator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Queryable<?> getSubQueryable() {
        return subQueryable;
    }
}
