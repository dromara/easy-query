package com.easy.query.core.expression.sql.expression;

import com.easy.query.core.context.QueryRuntimeContext;

import java.util.List;

/**
 * create time 2023/4/23 16:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySQLExpression extends SQLExpression {
    QueryRuntimeContext getRuntimeContext();
    List<EntityTableSQLExpression> getTables();
    default EntityTableSQLExpression getTable(int index){
        return getTables().get(index);
    }

    @Override
    EntitySQLExpression cloneSQLExpression();
}
