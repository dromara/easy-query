package com.easy.query.core.util;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * create time 2025/5/22 08:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasySQLJoinUtil {
    /**
     * 用来解决不进行select的时候直接query.where.join时应该将join前的表达式作为匿名表达式
     * @param clientQueryable
     * @return
     * @param <T1>
     */
    public static  <T1> ClientQueryable<T1> getJoinBaseQueryable(ClientQueryable<T1> clientQueryable){
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = clientQueryable.getSQLEntityExpressionBuilder();
        if(EasySQLExpressionUtil.hasAnyOperate(sqlEntityExpressionBuilder)){
            return clientQueryable.select(clientQueryable.queryClass());
        }
        return clientQueryable;

    }
}
