package com.easy.query.core.sharding.api.route.time;

import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ActualTable;
import com.easy.query.core.sharding.common.IgnoreCaseStringComparator;
import com.easy.query.core.sharding.route.table.abstraction.AbstractTableRoute;

import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * create time 2023/6/13 18:53
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractTimeTableRoute<TEntity> extends AbstractTableRoute<TEntity> {
    /**
     * 如果shardingValue本身就是LocalDateTime
     * (LocalDateTime) shardingValue
     * @param shardingValue
     * @return
     */
    protected abstract LocalDateTime convertLocalDateTime(Object shardingValue);
    protected Comparator<String> getTailComparator(){
        return IgnoreCaseStringComparator.DEFAULT;
    }
    protected abstract String formatShardingValue(LocalDateTime time);

    @Override
    protected RouteFunction<ActualTable> getRouteFilter(TableAvailable table, Object shardingValue, ShardingOperatorEnum shardingOperator, boolean withEntity) {
        LocalDateTime shardingTime = convertLocalDateTime(shardingValue);
        String tail = formatShardingValue(shardingTime);
        String tableName = table.getTableName() + tableSeparator() + tail;
        switch (shardingOperator) {
            case GREATER_THAN:
            case GREATER_THAN_OR_EQUAL:
                return t -> getTailComparator().compare(tableName, t.getActualTableName()) <= 0;
            case LESS_THAN: {
                //如果小于月初那么月初的表是不需要被查询的 如果小于年初也不需要查询
                if (lessThanTimeStart(shardingTime)) {
                    return t -> getTailComparator().compare(tableName, t.getActualTableName()) > 0;
                }
                return t -> getTailComparator().compare(tableName, t.getActualTableName()) >= 0;
            }
            case LESS_THAN_OR_EQUAL:
                return t -> getTailComparator().compare(tableName, t.getActualTableName()) >= 0;

            case EQUAL:
                return t -> getTailComparator().compare(tableName, t.getActualTableName()) == 0;
            default:
                return t -> true;
        }
    }

    /**
     *
     * @param shardingValue
     * @return
     */
    public abstract boolean lessThanTimeStart(LocalDateTime shardingValue);
}
