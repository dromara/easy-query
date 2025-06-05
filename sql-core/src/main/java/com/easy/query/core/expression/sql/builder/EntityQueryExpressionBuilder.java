package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import org.jetbrains.annotations.Nullable;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * create time 2023/3/3 22:17
 */
public interface EntityQueryExpressionBuilder extends EntityPredicateExpressionBuilder, LambdaEntityExpressionBuilder {
    boolean isEmpty();

    default boolean isNotEmpty() {
        return !isEmpty();
    }

    SQLBuilderSegment getProjects();

    PredicateSegment getWhere();

    long getOffset();

    void setOffset(long offset);

    long getRows();

    void setRows(long rows);

    boolean hasLimit();


    boolean hasWhere();

    PredicateSegment getHaving();

    boolean hasHaving();

    SQLBuilderSegment getGroup();

    boolean isDistinct();

    void setDistinct(boolean distinct);

    boolean hasGroup();

    OrderBySQLBuilderSegment getOrder();

    boolean hasOrder();

    @Nullable
    PredicateSegment getSQLWhereWithQueryFilter();

    @Override
    EntityQueryExpressionBuilder cloneEntityExpressionBuilder();

    @Override
    EntityQuerySQLExpression toExpression();
}
