package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
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
    public PrepareParseResult parse(EasyEntitySqlExpression easyEntitySqlExpression) {
        Set<Class<?>> shardingEntities = getShardingEntities(easyEntitySqlExpression);
        return new PrepareParseResult(shardingEntities, easyEntitySqlExpression);
//        if (ArrayUtil.isEmpty(shardingEntities)) {
//            return new PrepareParseResult(shardingEntities, easyEntitySqlExpression);
//        }
//        if (easyEntitySqlExpression instanceof EasyQuerySqlExpression) {
//
//        }
//        throw new UnsupportedOperationException(ClassUtil.getInstanceSimpleName(easyEntitySqlExpression));
    }

    private Set<Class<?>> getShardingEntities(EasyEntitySqlExpression easyEntitySqlExpression) {
        Set<Class<?>> shardingEntities = new LinkedHashSet<>(easyEntitySqlExpression.getTables().size());
        for (EasyTableSqlExpression table : easyEntitySqlExpression.getTables()) {
            if (!table.tableNameIsAs() && table.getEntityMetadata().isSharding()) {
                shardingEntities.add(table.getEntityMetadata().getEntityClass());
            }
        }
        return shardingEntities;
    }
}
