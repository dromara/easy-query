package com.easy.query.core.expression.sql.expression;

import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * create time 2023/4/22 21:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityQuerySQLExpression extends EntityPredicateSQLExpression {

    SQLBuilderSegment getProjects();
    void setProjects(SQLBuilderSegment projects);
    void setWhere(PredicateSegment where);

    SQLBuilderSegment getGroup();
    void setGroup(SQLBuilderSegment group);

    PredicateSegment getHaving();

    void setHaving(PredicateSegment having);

    SQLBuilderSegment getOrder();

    void setOrder(SQLBuilderSegment order);

    long getOffset();

    void setOffset(long offset);

    long getRows();

    void setRows(long rows);

    void setDistinct(boolean distinct);
    boolean isDistinct();

    boolean hasLimit();
//    List<EntityQuerySQLExpression> getIncludes();
//    void setIncludes(List<EntityQuerySQLExpression> includes);


    @Override
    EntityQuerySQLExpression cloneSQLExpression();
}
