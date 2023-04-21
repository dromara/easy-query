package com.easy.query.core.expression.sql;

import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.sql.def.EasyUpdateExpression;

/**
 * @FileName: SqlEntityDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:30
 * @author xuejiaming
 */
public interface EntityInsertExpression extends EntityExpression {
    SqlBuilderSegment getColumns();
    String toSql(Object entity);
    default EntityInsertExpression cloneSqlInsertExpression(){
        return (EntityInsertExpression)cloneEntityExpression();
    }
}