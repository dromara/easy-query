package com.easy.query.springshardingdemo.sharding;

import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ActualTable;
import com.easy.query.core.sharding.route.table.abstraction.AbstractTableRoute;
import com.easy.query.springshardingdemo.domain.OrderTest;

/**
 * create time 2023/11/15 14:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class OrderTestStringTableRoute extends AbstractTableRoute<OrderTest> {
    protected String formatShardingValue(Object shardingValue) {
        String value = String.valueOf(shardingValue);
        return value;
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
