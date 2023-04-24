package com.easy.query.core.sharding.rule.table.abstraction;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.sharding.enums.ShardingOperatorEnum;
import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.sharding.rule.table.TableRouteRule;

import java.util.Collection;

/**
 * create time 2023/4/19 09:41
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractTableRouteRule implements TableRouteRule {

    @Override
    public String mapTableName(String originalTableName) {
        return originalTableName;
    }

    @Override
    public RouteFunction<String> routeFilter(Object shardingValue, ShardingOperatorEnum shardingOperator, String propertyName,boolean isMainShardingProperty) {
       if(isMainShardingProperty){
           return getRouteFilter(shardingValue,shardingOperator);
       }
       return getExtraRouteFilter(shardingValue,shardingOperator,propertyName);
    }

    protected abstract RouteFunction<String> getRouteFilter(Object shardingValue,ShardingOperatorEnum shardingOperator);
    protected  RouteFunction<String> getExtraRouteFilter(Object shardingValue,ShardingOperatorEnum shardingOperator,String propertyName){
        throw new UnsupportedOperationException(propertyName+" sharding route filter");
    }
    @Override
    public Collection<String> beforeFilterTableName(Collection<String> allTableNames) {
        return allTableNames;
    }

    @Override
    public Collection<TableRouteUnit> afterFilterTableName(Collection<String> allTableNames, Collection<String> beforeTableNames, Collection<TableRouteUnit> filterRouteUnits) {
        return filterRouteUnits;
    }
}
