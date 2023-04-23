package com.easy.query.core.expression.sql.expression;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

import java.util.List;

/**
 * create time 2023/4/22 21:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyQuerySqlExpression extends EasyEntityPredicateSqlExpression {
    EasyQueryRuntimeContext getRuntimeContext();

    SqlBuilderSegment getProjects();
    void setProjects(SqlBuilderSegment projects);
    void setWhere(PredicateSegment where);

    SqlBuilderSegment getGroup();
    void setGroup(SqlBuilderSegment group);

    PredicateSegment getHaving();

    void setHaving(PredicateSegment having);

    SqlBuilderSegment getOrder();

    void setOrder(SqlBuilderSegment order);

    long getOffset();

    void setOffset(long offset);

    long getRows();

    void setRows(long rows);

    void setDistinct(boolean distinct);

    boolean hasLimit();
}
