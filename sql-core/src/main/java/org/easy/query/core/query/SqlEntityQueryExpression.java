package org.easy.query.core.query;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.segment.condition.PredicateSegment;

import java.util.List;

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


     boolean hasWhere();

     PredicateSegment getHaving() ;

     boolean hasHaving();
     SqlBuilderSegment getGroup();

     boolean hasGroup();

     SqlBuilderSegment getOrder() ;

     boolean hasOrder();
    SqlEntityQueryExpression cloneSqlQueryExpression();
}
