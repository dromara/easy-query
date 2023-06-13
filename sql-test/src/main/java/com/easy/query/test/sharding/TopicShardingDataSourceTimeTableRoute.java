package com.easy.query.test.sharding;

import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ActualTable;
import com.easy.query.core.sharding.route.table.abstraction.AbstractTableRoute;
import com.easy.query.test.entity.TopicShardingDataSourceTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * create time 2023/5/11 22:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class TopicShardingDataSourceTimeTableRoute extends AbstractTableRoute<TopicShardingDataSourceTime> {
    @Override
    protected RouteFunction<ActualTable> getRouteFilter(TableAvailable table, Object shardingValue, ShardingOperatorEnum shardingOperator, boolean withEntity) {
        LocalDateTime createTime = (LocalDateTime) shardingValue;
        String tail = createTime.format(DateTimeFormatter.ofPattern("yyyyMM"));
        String tableName = table.getTableName()+"_" + tail;
        switch (shardingOperator){
            case GREATER_THAN:
            case GREATER_THAN_OR_EQUAL:
                return t-> tableName.compareToIgnoreCase(t.getActualTableName())<=0;
            case LESS_THAN:
            {
                //如果小于月初那么月初的表是不需要被查询的
                LocalDateTime timeMonthFirstDay = createTime.toLocalDate().atStartOfDay();
                if(createTime.isEqual(timeMonthFirstDay)){
                    return t->tableName.compareToIgnoreCase(t.getActualTableName())>0;
                }
                return t->tableName.compareToIgnoreCase(t.getActualTableName())>=0;
            }
            case LESS_THAN_OR_EQUAL:
                return t->tableName.compareToIgnoreCase(t.getActualTableName())>=0;

            case EQUAL:
                return t->tableName.compareToIgnoreCase(t.getActualTableName())==0;
            default:return t->true;
        }
    }
}
