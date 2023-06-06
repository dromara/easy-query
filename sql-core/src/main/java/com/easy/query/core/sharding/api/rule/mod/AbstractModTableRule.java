package com.easy.query.core.sharding.api.rule.mod;

import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ActualTable;
import com.easy.query.core.sharding.rule.table.abstraction.AbstractTableRouteRule;
import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2023/5/21 14:48
 * 分片键字符串hashcode进行路由
 *
 * @author xuejiaming
 */
public abstract class AbstractModTableRule<TEntity> extends AbstractTableRouteRule<TEntity> {
    protected abstract int mod();

    protected abstract int tailLength();

    protected char paddingWord() {
        return '0';
    }

    protected String tableSeparator() {
        return "_";
    }

    protected String formatShardingValue(Object shardingValue) {
        String value = String.valueOf(shardingValue);
        int tail = Math.abs(value.hashCode() % mod());
        return EasyStringUtil.leftPad(String.valueOf(tail), tailLength(), paddingWord());
    }

    @Override
    protected RouteFunction<ActualTable> getRouteFilter(TableAvailable table, Object shardingValue, ShardingOperatorEnum shardingOperator, boolean withEntity) {
        String tail = formatShardingValue(shardingValue);
        String tableName = table.getTableName() + tableSeparator() + tail;
        switch (shardingOperator) {
            case EQUAL:
                return t -> tableName.compareToIgnoreCase(t.getActualTableName()) == 0;
            default:
                return t -> true;
        }
    }
}
