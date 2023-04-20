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
    public RouteFunction<String> routeFilter(Comparable<?> shardingValue, ShardingOperatorEnum shardingOperator, String propertyName,boolean isMainShardingProperty) {
       if(isMainShardingProperty){
           return getRouteFilter(shardingValue,shardingOperator);
       }
       return getExtraRouteFilter(shardingValue,shardingOperator,propertyName);
    }

    protected abstract RouteFunction<String> getRouteFilter(Comparable<?> shardingValue,ShardingOperatorEnum shardingOperator);
    protected abstract RouteFunction<String> getExtraRouteFilter(Comparable<?> shardingValue,ShardingOperatorEnum shardingOperator,String propertyName);
    @Override
    public Collection<String> beforeFilterTableName(Collection<String> allTableNames) {
        return allTableNames;
    }

    @Override
    public Collection<TableRouteUnit> afterFilterTableName(Collection<String> allTableNames, Collection<String> beforeTableNames, Collection<TableRouteUnit> filterRouteUnits) {
        return filterRouteUnits;
    }
}
