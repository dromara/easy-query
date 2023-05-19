package com.easy.query.core.sharding.rule.def;

import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ActualTable;
import com.easy.query.core.sharding.rule.table.abstraction.AbstractTableRouteRule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * create time 2023/5/19 14:34
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractLocalDateTimeMonthTableRule<TEntity> extends AbstractTableRouteRule<TEntity> {
    protected LocalDateTime convertLocalDateTime(Object shardingValue) {
        return (LocalDateTime) shardingValue;
    }
    protected String formatShardingValue(LocalDateTime time){
        return time.format(DateTimeFormatter.ofPattern("yyyyMM"));
    }

    /**
     * 表连接符比如order_1,返回就是"_";
     * @return
     */
    protected String tableSeparator(){
        return "_";
    }

    @Override
    protected RouteFunction<ActualTable> getRouteFilter(TableAvailable table, Object shardingValue, ShardingOperatorEnum shardingOperator, boolean withEntity) {
        LocalDateTime shardingTime = convertLocalDateTime(shardingValue);
        String month = formatShardingValue(shardingTime);
        String tableName = table.getTableName()+tableSeparator() + month;
        switch (shardingOperator) {
            case GREATER_THAN:
            case GREATER_THAN_OR_EQUAL:
                return t -> tableName.compareToIgnoreCase(t.getActualTableName()) <= 0;
            case LESS_THAN: {
                //如果小于月初那么月初的表是不需要被查询的
                LocalDateTime timeMonthFirstDay = shardingTime.toLocalDate().atStartOfDay();
                if (shardingTime.isEqual(timeMonthFirstDay)) {
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
}
