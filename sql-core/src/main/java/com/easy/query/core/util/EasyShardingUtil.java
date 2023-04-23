package com.easy.query.core.util;

import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

/**
 * create time 2023/4/8 21:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyShardingUtil {
    public static boolean isShardingQuery(EntityQueryExpressionBuilder entityQueryExpression){
        for (EntityTableExpressionBuilder table : entityQueryExpression.getTables()) {
            if(table instanceof AnonymousEntityTableExpressionBuilder){
                continue;
            }
            if(!table.tableNameIsAs()&&table.getEntityMetadata().isSharding()){
                return true;
            }
        }
        return false;
    }
}
