package com.easy.query.core.expression.sql;

import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.def.EasyQueryExpression;

/**
 * @FileName: SqlEntityExpressionSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 22:17
 * @author xuejiaming
 */
public interface EntityQueryExpression extends EntityExpression,LambdaEntityExpression {
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
     default EasyQueryExpression cloneSqlQueryExpression(){
         return (EasyQueryExpression)cloneEntityExpression();
     }

}
