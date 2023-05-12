package com.easy.query.test.sharding;

import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.sharding.rule.datasource.abstraction.AbstractDataSourceRouteRule;
import com.easy.query.test.entity.TopicShardingDataSourceTime;

import java.time.LocalDateTime;

/**
 * create time 2023/5/11 22:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class TopicShardingDataSourceTimeDataSourceRule extends AbstractDataSourceRouteRule<TopicShardingDataSourceTime> {
    @Override
    protected RouteFunction<String> getRouteFilter(Object shardingValue, ShardingOperatorEnum shardingOperator, boolean withEntity) {
        LocalDateTime createTime = (LocalDateTime) shardingValue;
        String dataSource = "ds" + createTime.getYear();
        switch (shardingOperator){
            case GREATER_THAN:
            case GREATER_THAN_OR_EQUAL:
                return t-> dataSource.compareToIgnoreCase(getTableRemoveTable(t))<=0;
            case LESS_THAN:
            {
                //如果小于月初那么月初的表是不需要被查询的
                LocalDateTime timeYearFirstDay = LocalDateTime.of(createTime.getYear(),1,1,0,0,0);
                if(createTime.isEqual(timeYearFirstDay)){
                    return t->dataSource.compareToIgnoreCase(getTableRemoveTable(t))>0;
                }
                return t->dataSource.compareToIgnoreCase(getTableRemoveTable(t))>=0;
            }
            case LESS_THAN_OR_EQUAL:
                return t->dataSource.compareToIgnoreCase(getTableRemoveTable(t))>=0;

            case EQUAL:
                return t->dataSource.compareToIgnoreCase(getTableRemoveTable(t))==0;
            default:return t->true;
        }
    }
    private String getTableRemoveTable(String fullTableName) {
        return fullTableName.split("\\.")[0];
    }
}
