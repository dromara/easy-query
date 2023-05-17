package com.easy.query.core.expression.sql.expression;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;

import java.util.List;

/**
 * create time 2023/4/23 16:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyEntitySQLExpression extends EasySQLExpression {
    EasyQueryRuntimeContext getRuntimeContext();
    List<EasyTableSQLExpression> getTables();
    default EasyTableSQLExpression getTable(int index){
        return getTables().get(index);
    }

    @Override
    EasyEntitySQLExpression cloneSQLExpression();
}
