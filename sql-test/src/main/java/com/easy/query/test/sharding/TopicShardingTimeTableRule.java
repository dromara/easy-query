package com.easy.query.test.sharding;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.metadata.ActualTable;
import com.easy.query.core.sharding.rule.table.abstraction.AbstractTableRouteRule;
import com.easy.query.test.entity.TopicShardingTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * create time 2023/4/23 23:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class TopicShardingTimeTableRule extends AbstractTableRouteRule<TopicShardingTime> {

    @Override
    protected RouteFunction<ActualTable> getRouteFilter(Object shardingValue, ShardingOperatorEnum shardingOperator, boolean withEntity) {
        LocalDateTime createTime = (LocalDateTime) shardingValue;
        String month = createTime.format(DateTimeFormatter.ofPattern("yyyyMM"));
        String tableName = "t_topic_sharding_time_" + month;
        switch (shardingOperator) {
            case GREATER_THAN:
            case GREATER_THAN_OR_EQUAL:
                return t -> tableName.compareToIgnoreCase(t.getActualTableName()) <= 0;
            case LESS_THAN: {
                //如果小于月初那么月初的表是不需要被查询的
                LocalDateTime timeMonthFirstDay = createTime.toLocalDate().atStartOfDay();
                if (createTime.isEqual(timeMonthFirstDay)) {
                    return t -> tableName.compareToIgnoreCase(t.getActualTableName()) > 0;
                }
                return t -> tableName.compareToIgnoreCase(t.getActualTableName()) >= 0;
            }
            case LESS_THAN_OR_EQUAL:
                return t -> tableName.compareToIgnoreCase(t.getActualTableName()) >= 0;

            case EQUAL:
                return t -> tableName.compareToIgnoreCase(t.getActualTableName()) == 0;
            default:
                return t -> true;
        }
    }
    @Override
    protected RouteFunction<ActualTable> getExtraRouteFilter(Object shardingValue, ShardingOperatorEnum shardingOperator, String propertyName) {
        if (Objects.equals(propertyName, "id")) {
            return getIdRouteFilter(shardingValue, shardingOperator);
        }
        return super.getExtraRouteFilter(shardingValue, shardingOperator, propertyName);
    }

    private RouteFunction<ActualTable> getIdRouteFilter(Object shardingValue, ShardingOperatorEnum shardingOperator) {
        String id = shardingValue.toString();
        if(id.length()<=6){
            return t->true;
        }
        String month = id.substring(id.length() - 6);
        String tableName = "t_topic_sharding_time_" + month;
        switch (shardingOperator) {
            case EQUAL:
                return t -> tableName.compareToIgnoreCase(t.getActualTableName()) == 0;
            default:
                return t -> true;
        }
    }
}
