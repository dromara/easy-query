package com.easy.query.core.expression.sql.expression;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;

import java.util.List;

/**
 * create time 2023/4/23 16:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySQLExpression extends SQLExpression {
    EasyQueryRuntimeContext getRuntimeContext();
    List<TableSQLExpression> getTables();
    default TableSQLExpression getTable(int index){
        return getTables().get(index);
    }

    @Override
    EntitySQLExpression cloneSQLExpression();
}
