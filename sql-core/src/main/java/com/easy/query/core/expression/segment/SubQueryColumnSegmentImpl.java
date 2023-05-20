package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/5/20 10:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class SubQueryColumnSegmentImpl implements SubQueryColumnSegment{
    private final TableAvailable table;
    private final Queryable<?> subQueryable;
    private final String alias;
    private final QueryRuntimeContext runtimeContext;

    public SubQueryColumnSegmentImpl(TableAvailable table, Queryable<?> subQueryable, String alias, QueryRuntimeContext runtimeContext){

        this.table = table;
        this.subQueryable = subQueryable;
        this.alias = alias;
        this.runtimeContext = runtimeContext;
    }
    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return null;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        String queryableSQL = subQueryable.toSQL(toSQLContext);
        StringBuilder sql = new StringBuilder();
        sql.append("(");
        sql.append(queryableSQL);
        sql.append(")");
        if(alias!=null){
            sql.append(" AS ").append(EasySQLExpressionUtil.getQuoteName(runtimeContext,alias));
        }
        return sql.toString();
    }

    @Override
    public Queryable<?> getSubQueryable() {
        return subQueryable;
    }

    @Override
    public SubQueryColumnSegment cloneSQLEntitySegment() {
        return new SubQueryColumnSegmentImpl(table,subQueryable.cloneQueryable(),alias,runtimeContext);
    }
}
