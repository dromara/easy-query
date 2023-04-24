package com.easy.query.sharding;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.sharding.enums.ShardingOperatorEnum;
import com.easy.query.core.sharding.rule.table.abstraction.AbstractTableRouteRule;
import com.easy.query.entity.TopicSharding;

import java.util.Objects;

/**
 * create time 2023/4/23 23:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class TopicShardingTableRule extends AbstractTableRouteRule {
    @Override
    public Class<?> entityClass() {
        return TopicSharding.class;
    }

    @Override
    protected RouteFunction<String> getRouteFilter(Object shardingValue, ShardingOperatorEnum shardingOperator) {
        String tail = String.valueOf(shardingValue.toString().hashCode() % 3);
        if(Objects.equals(ShardingOperatorEnum.EQUAL,shardingOperator)){
            return t->{
                return Objects.equals(t,"ds0.t_topic_sharding_"+tail);
            };
        }
        return t->true;
    }
}
