package com.easy.query.core.sharding.api.route.time;

import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ActualTable;
import com.easy.query.core.sharding.route.table.abstraction.AbstractTableRoute;
import com.easy.query.core.util.EasyUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * create time 2023/5/19 14:34
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractWeekTableRoute<TEntity> extends AbstractTableRoute<TEntity> {
    /**
     * 如果shardingValue本身就是LocalDateTime
     * (LocalDateTime) shardingValue
     * @param shardingValue
     * @return
     */
    protected abstract LocalDateTime convertLocalDateTime(Object shardingValue);
    protected String formatShardingValue(LocalDateTime time){
        LocalDateTime weekEnd = EasyUtil.getWeekEnd(time);
        String dd = weekEnd.format(DateTimeFormatter.ofPattern("dd"));
        return EasyUtil.getWeekStart(time).format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"_"+dd;
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
        String year = formatShardingValue(shardingTime);
        String tableName = table.getTableName()+tableSeparator() + year;
        switch (shardingOperator) {
            case GREATER_THAN:
            case GREATER_THAN_OR_EQUAL:
                return t -> tableName.compareToIgnoreCase(t.getActualTableName()) <= 0;
            case LESS_THAN: {
                //如果小于月初那么月初的表是不需要被查询的
                LocalDateTime timeWeekFirstDay = EasyUtil.getWeekStart(shardingTime);
                if (shardingTime.isEqual(timeWeekFirstDay)) {
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
