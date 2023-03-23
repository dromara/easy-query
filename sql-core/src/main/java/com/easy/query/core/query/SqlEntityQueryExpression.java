package com.easy.query.core.query;

import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * @FileName: SqlEntityExpressionSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 22:17
 * @Created by xuejiaming
 */
public interface SqlEntityQueryExpression extends SqlEntityExpression{
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

    /**
     * 创建一个新的EntityQueryExpression但是会共享同一个上下文
     * @return
     */
    SqlEntityQueryExpression cloneSqlQueryExpression();
}
