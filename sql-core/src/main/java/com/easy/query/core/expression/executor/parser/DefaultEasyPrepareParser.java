package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;

import java.util.LinkedHashSet;

/**
 * create time 2023/4/9 22:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyPrepareParser implements EasyPrepareParser{
    @Override
    public PrepareParseResult parse(EntityQueryExpression entityQueryExpression) {
        LinkedHashSet<Class<?>> shardingEntities = new LinkedHashSet<>(entityQueryExpression.getTables().size());
        for (EntityTableExpression table : entityQueryExpression.getTables()) {
            if(!table.tableNameIsAs()&&table.getEntityMetadata().isSharding()){
                shardingEntities.add(table.getEntityClass());
            }
        }
        return new PrepareParseResult(shardingEntities);
    }
}
