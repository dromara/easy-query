package com.easy.query.test.sharding;

import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.sharding.route.datasource.abstraction.AbstractDataSourceRoute;
import com.easy.query.test.entity.TopicShardingDataSource;

import java.time.LocalDateTime;

/**
 * create time 2023/5/19 13:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class TopicShardingDataSourceRoute extends AbstractDataSourceRoute<TopicShardingDataSource> {
    @Override
    protected RouteFunction<String> getRouteFilter(TableAvailable table, Object shardingValue, ShardingOperatorEnum shardingOperator, boolean withEntity) {
        LocalDateTime createTime = (LocalDateTime) shardingValue;
        String dataSource = "ds" + createTime.getYear();
        switch (shardingOperator){
            case GREATER_THAN:
            case GREATER_THAN_OR_EQUAL:
                return ds-> dataSource.compareToIgnoreCase(ds)<=0;
            case LESS_THAN:
            {
                //如果小于月初那么月初的表是不需要被查询的
                LocalDateTime timeYearFirstDay = LocalDateTime.of(createTime.getYear(),1,1,0,0,0);
                if(createTime.isEqual(timeYearFirstDay)){
                    return ds->dataSource.compareToIgnoreCase(ds)>0;
                }
                return ds->dataSource.compareToIgnoreCase(ds)>=0;
            }
            case LESS_THAN_OR_EQUAL:
                return ds->dataSource.compareToIgnoreCase(ds)>=0;

            case EQUAL:
                return ds->dataSource.compareToIgnoreCase(ds)==0;
            default:return t->true;
        }
    }
}
