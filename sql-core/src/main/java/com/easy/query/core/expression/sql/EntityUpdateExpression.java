package com.easy.query.core.expression.sql;

import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.def.EasyQueryExpression;
import com.easy.query.core.expression.sql.def.EasyUpdateExpression;

/**
 * @FileName: SqlEntityUpdateExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 17:04
 * @author xuejiaming
 */
public interface EntityUpdateExpression extends EntityExpression {

     SqlBuilderSegment getSetColumns();
     boolean hasSetColumns();

     PredicateSegment getWhere();
     boolean hasWhere();
     SqlBuilderSegment getSetIgnoreColumns();
     boolean hasSetIgnoreColumns();
     SqlBuilderSegment getWhereColumns();
     boolean hasWhereColumns();

     String toSql(Object entity);
     default EasyUpdateExpression cloneSqlUpdateExpression(){
          return (EasyUpdateExpression)cloneEntityExpression();
     }
}
