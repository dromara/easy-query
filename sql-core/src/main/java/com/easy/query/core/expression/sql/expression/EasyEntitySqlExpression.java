package com.easy.query.core.expression.sql.expression;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.enums.ExecuteMethodEnum;

import java.util.List;

/**
 * create time 2023/4/23 16:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyEntitySqlExpression extends EasySqlExpression {
    EasyQueryRuntimeContext getRuntimeContext();
    List<EasyTableSqlExpression> getTables();
    default EasyTableSqlExpression getTable(int index){
        return getTables().get(index);
    }

    @Override
    EasyEntitySqlExpression cloneSqlExpression();
}
