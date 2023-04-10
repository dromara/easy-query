package com.easy.query.core.util;

import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;

/**
 * create time 2023/4/8 21:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyShardingUtil {
    public static boolean isShardingQuery(EntityQueryExpression entityQueryExpression){
        for (EntityTableExpression table : entityQueryExpression.getTables()) {
            if(!table.tableNameIsAs()&&table.getEntityMetadata().isSharding()){
                return true;
            }
        }
        return false;
    }
}
