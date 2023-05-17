package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntitySegment;

/**
 * create time 2023/4/27 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnExistsSubQueryPredicate implements SubQueryPredicate{
    private final SQLPredicateCompare sqlPredicateCompare;
    private final EasyQueryRuntimeContext runtimeContext;
    private final TableAvailable table;
    private final Queryable<?> subQueryable;

    public ColumnExistsSubQueryPredicate(TableAvailable table, Queryable<?> subQueryable, SQLPredicateCompare sqlPredicateCompare, EasyQueryRuntimeContext runtimeContext) {
        this.table = table;
        this.subQueryable = subQueryable;
        this.sqlPredicateCompare = sqlPredicateCompare;
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
    public SQLEntitySegment cloneSQLEntitySegment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {

        StringBuilder sql = new StringBuilder();
        sql.append(sqlPredicateCompare.getSQL()).append(" (");
        String subQueryableSQL = subQueryable.toSQL(sqlParameterCollector);
        sql.append(subQueryableSQL).append(") ");
        return sql.toString();
    }

    @Override
    public SQLPredicateCompare getOperator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Queryable<?> getSubQueryable() {
        return subQueryable;
    }
}
