package com.easy.query.springshardingdemo.sharding;

import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.sharding.common.IgnoreCaseStringComparator;
import com.easy.query.core.sharding.route.datasource.abstraction.AbstractDataSourceRoute;
import com.easy.query.core.util.EasyUtil;
import com.easy.query.springshardingdemo.domain.OrderEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 * create time 2023/6/26 22:12
 * 文件说明
 *
 * @author xuejiaming
 */
@Component
public class OrderDataSourceRoute extends AbstractDataSourceRoute<OrderEntity> {
    protected Integer formatShardingValue(LocalDateTime time) {
        String year = time.format(DateTimeFormatter.ofPattern("yyyy"));
        return Integer.parseInt(year);
    }
    public boolean lessThanTimeStart(LocalDateTime shardingValue) {
        LocalDateTime timeYearFirstDay = EasyUtil.getYearStart(shardingValue);
        return shardingValue.isEqual(timeYearFirstDay);
    }

    protected Comparator<String> getDataSourceComparator(){
        return IgnoreCaseStringComparator.DEFAULT;
    }
    @Override
    protected RouteFunction<String> getRouteFilter(TableAvailable table, Object shardingValue, ShardingOperatorEnum shardingOperator, boolean withEntity) {
        //将分片键转成对应的类型
        LocalDateTime shardingTime = (LocalDateTime)shardingValue ;
        Integer intYear = formatShardingValue(shardingTime);
        String dataSourceName="ds"+String.valueOf((intYear-2020));//ds0 ds1 ds2 ds3....
        switch (shardingOperator) {
            case GREATER_THAN:
            case GREATER_THAN_OR_EQUAL:
                return ds -> getDataSourceComparator().compare(dataSourceName, ds) <= 0;
            case LESS_THAN: {
                //如果小于月初那么月初的表是不需要被查询的 如果小于年初也不需要查询
                if (lessThanTimeStart(shardingTime)) {
                    return ds -> getDataSourceComparator().compare(dataSourceName, ds) > 0;
                }
                return ds -> getDataSourceComparator().compare(dataSourceName, ds) >= 0;
            }
            case LESS_THAN_OR_EQUAL:
                return ds -> getDataSourceComparator().compare(dataSourceName, ds) >= 0;

            case EQUAL:
                return ds -> getDataSourceComparator().compare(dataSourceName,ds) == 0;
            default:
                return ds -> true;
        }
    }
}
