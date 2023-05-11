package com.easy.query.sharding;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.sharding.rule.table.abstraction.AbstractTableRouteRule;
import com.easy.query.core.util.StringUtil;
import com.easy.query.entity.TopicSharding;

/**
 * create time 2023/4/23 23:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class TopicShardingTableRule extends AbstractTableRouteRule<TopicSharding> {

    @Override
    protected RouteFunction<String> getRouteFilter(Object shardingValue, ShardingOperatorEnum shardingOperator,boolean withEntity) {
        String tail = String.valueOf(shardingValue.toString().hashCode() % 3);
        switch (shardingOperator){
            case EQUAL:return t-> StringUtil.endsWith(t,".t_topic_sharding_"+tail);
            default:return t->true;
        }
    }
}
