package com.easy.query.test.sharding;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.metadata.ActualTable;
import com.easy.query.core.sharding.rule.table.abstraction.AbstractTableRouteRule;
import com.easy.query.test.entity.TopicSharding;

/**
 * create time 2023/4/23 23:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class TopicShardingTableRule extends AbstractTableRouteRule<TopicSharding> {

    @Override
    protected RouteFunction<ActualTable> getRouteFilter(Object shardingValue, ShardingOperatorEnum shardingOperator, boolean withEntity) {
        String tail = String.valueOf(shardingValue.toString().hashCode() % 3);
        String actualTableName = "t_topic_sharding_" + tail;
        switch (shardingOperator){
            case EQUAL:return t->  actualTableName.compareToIgnoreCase(t.getActualTableName())==0;
            default:return t->true;
        }
    }
}
