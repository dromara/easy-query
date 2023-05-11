package com.easy.query.sharding;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.sharding.rule.table.abstraction.AbstractTableRouteRule;
import com.easy.query.entity.TopicShardingTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * create time 2023/4/23 23:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class TopicShardingTimeTableRule extends AbstractTableRouteRule<TopicShardingTime> {

    @Override
    protected RouteFunction<String> getRouteFilter(Object shardingValue, ShardingOperatorEnum shardingOperator,boolean withEntity) {
        LocalDateTime createTime = (LocalDateTime) shardingValue;
        String tail = createTime.format(DateTimeFormatter.ofPattern("yyyyMM"));
        String tableName = "t_topic_sharding_time_" + tail;
        switch (shardingOperator){
            case GREATER_THAN:
            case GREATER_THAN_OR_EQUAL:
                return t-> tableName.compareToIgnoreCase(getTableRemoveDataSource(t))<=0;
            case LESS_THAN:
            {
                //如果小于月初那么月初的表是不需要被查询的
                LocalDateTime timeMonthFirstDay = createTime.toLocalDate().atStartOfDay();
                if(createTime.isEqual(timeMonthFirstDay)){
                    return t->tableName.compareToIgnoreCase(getTableRemoveDataSource(t))>0;
                }
                return t->tableName.compareToIgnoreCase(getTableRemoveDataSource(t))>=0;
            }
            case LESS_THAN_OR_EQUAL:
                return t->tableName.compareToIgnoreCase(getTableRemoveDataSource(t))>=0;

            case EQUAL:
                return t->tableName.compareToIgnoreCase(getTableRemoveDataSource(t))==0;
            default:return t->true;
        }
    }
    private String getTableRemoveDataSource(String fullTableName) {
        return fullTableName.split("\\.")[1];
    }
}
