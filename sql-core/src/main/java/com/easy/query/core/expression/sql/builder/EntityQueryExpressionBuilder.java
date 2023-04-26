package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.QueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;

/**
 * @FileName: SqlEntityExpressionSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 22:17
 * @author xuejiaming
 */
public interface EntityQueryExpressionBuilder extends EntityPredicateExpressionBuilder, LambdaEntityExpressionBuilder {
    boolean isEmpty();
    default boolean isNotEmpty() {
        return !isEmpty();
    }

    SqlBuilderSegment getProjects();
    PredicateSegment getWhere();
     long getOffset();

     void setOffset(long offset);

     long getRows();

     void setRows(long rows);

     boolean hasLimit();


     boolean hasWhere();

     PredicateSegment getHaving() ;

     boolean hasHaving();
     SqlBuilderSegment getGroup();
     boolean isDistinct();
     void setDistinct(boolean distinct);

     boolean hasGroup();

     SqlBuilderSegment getOrder() ;

     boolean hasOrder();
    @Override
    EntityQueryExpressionBuilder cloneEntityExpressionBuilder();

    @Override
    EasyQuerySqlExpression toExpression();
}
