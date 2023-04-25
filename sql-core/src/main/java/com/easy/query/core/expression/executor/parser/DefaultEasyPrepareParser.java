package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.ClassUtil;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * create time 2023/4/9 22:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyPrepareParser implements EasyPrepareParser {
    @Override
    public Set<Class<?>> parse(EntityExpressionBuilder entityExpressionBuilder) {
        return getShardingEntities(entityExpressionBuilder);
    }

    private Set<Class<?>> getShardingEntities(EntityExpressionBuilder entityExpressionBuilder) {
        Set<Class<?>> shardingEntities = new LinkedHashSet<>(entityExpressionBuilder.getTables().size());
        for (EntityTableExpressionBuilder table : entityExpressionBuilder.getTables()) {
            if (!table.tableNameIsAs() && table.getEntityMetadata().isSharding()) {
                shardingEntities.add(table.getEntityMetadata().getEntityClass());
            }
        }
        return shardingEntities;
    }
}
