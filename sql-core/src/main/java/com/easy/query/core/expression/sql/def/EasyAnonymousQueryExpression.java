package com.easy.query.core.expression.sql.def;

import com.easy.query.core.expression.sql.factory.EasyExpressionFactory;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.expression.sql.ExpressionContext;
import com.easy.query.core.expression.sql.SqlEntityQueryExpression;
import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2023/3/31 10:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyAnonymousQueryExpression extends EasyQueryExpression implements SqlEntityQueryExpression {
    private final String sql;

    public EasyAnonymousQueryExpression(String sql, ExpressionContext queryExpressionContext) {
        super(queryExpressionContext);
        this.sql = sql;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public SqlBuilderSegment getProjects() {
        return super.getProjects();
    }

    @Override
    public PredicateSegment getWhere() {
        return super.getWhere();
    }

    @Override
    public long getOffset() {
        return super.getOffset();
    }

    @Override
    public void setOffset(long offset) {
        super.setOffset(offset);
    }

    @Override
    public long getRows() {
        return super.getRows();
    }

    @Override
    public void setRows(long rows) {
        super.setRows(rows);
    }

    @Override
    public boolean hasLimit() {
        return super.hasLimit();
    }

    @Override
    public boolean hasWhere() {
        return super.hasWhere();
    }

    @Override
    public PredicateSegment getHaving() {
        return super.getHaving();
    }

    @Override
    public boolean hasHaving() {
        return super.hasHaving();
    }

    @Override
    public SqlBuilderSegment getGroup() {
        return super.getGroup();
    }

    @Override
    public boolean hasGroup() {
        return super.hasGroup();
    }

    @Override
    public SqlBuilderSegment getOrder() {
        return super.getOrder();
    }

    @Override
    public boolean hasOrder() {
        return super.hasOrder();
    }

    @Override
    public boolean isDistinct() {
        return super.isDistinct();
    }

    @Override
    public void setDistinct(boolean distinct) {
        super.setDistinct(distinct);
    }

    @Override
    public boolean isExpression() {
        return super.isExpression();
    }


    @Override
    public String toSql() {
        return sql;
    }



    @Override
    public EntityQueryExpression cloneSqlQueryExpression() {
        ExpressionContext sqlExpressionContext = getExpressionContext();
        EasyExpressionFactory sqlExpressionFactory = getRuntimeContext().getSqlExpressionFactory();
        EasyAnonymousQueryExpression anonymousQueryExpression = (EasyAnonymousQueryExpression) sqlExpressionFactory.createAnonymousQueryExpression(sql,sqlExpressionContext);
        anonymousQueryExpression.tables.addAll(super.tables);
        return anonymousQueryExpression;
    }
}
