package com.easy.query.test.sharding;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ActualTable;
import com.easy.query.core.sharding.rule.def.AbstractLocalDateTimeMonthTableRule;
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
public class TopicShardingTimeTableRule extends AbstractLocalDateTimeMonthTableRule<TopicShardingTime> {

    @Override
    protected RouteFunction<ActualTable> getExtraRouteFilter(TableAvailable table, Object shardingValue, ShardingOperatorEnum shardingOperator, String propertyName) {
        if (Objects.equals(propertyName, "id")) {
            return getIdRouteFilter(table,shardingValue, shardingOperator);
        }
        return super.getExtraRouteFilter(table,shardingValue, shardingOperator, propertyName);
    }

    private RouteFunction<ActualTable> getIdRouteFilter(TableAvailable table,Object shardingValue, ShardingOperatorEnum shardingOperator) {
        String id = shardingValue.toString();
        if(id.length()<=6){
            return t->true;
        }
        String month = id.substring(id.length() - 6);
        String tableName = table.getTableName()+"_" + month;
        switch (shardingOperator) {
            case EQUAL:
                return t -> tableName.compareToIgnoreCase(t.getActualTableName()) == 0;
            default:
                return t -> true;
        }
    }
}
