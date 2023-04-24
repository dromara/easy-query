package com.easy.query.core.sharding.rule.table;

import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.sharding.rule.RouteRule;
import com.easy.query.core.sharding.rule.RouteRuleFilter;
import com.easy.query.core.sharding.route.table.TableRouteUnit;

import java.util.Collection;

/**
 * create time 2023/4/18 23:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableRouteRule extends RouteRule, RouteRuleFilter {
    /**
     * 用来映射过滤表名
     * @param originalTableName data source.actual table name
     * @return 过滤后的表名默认返回 {@param originalTableName} 用于{@link RouteFunction<String>}方法的入参
     */
    String mapTableName(String originalTableName);

    /**
     * 用于过滤前设置表名 data source.actual table name 全部集合
     * @param allTableNames 过滤前的表名
     * @return 过滤后的表名默认返回 {@param allTableNames} 用于{@link RouteFunction<String>}方法的入参
     */
    Collection<String> beforeFilterTableName(Collection<String> allTableNames);

    /**
     * 后置过滤器用来返回过滤后的表名,比如可以设置表名大于10张时,默认返回最新的10张表而不是全部表名
     * @param allTableNames 所有的表名
     * @param beforeTableNames 前置筛选后的表名
     * @param filterRouteUnits 过滤后的路由执行单元
     * @return 过滤后的路由执行单元,默认就是 {@param filterRouteUnits}
     */
    Collection<TableRouteUnit> afterFilterTableName(Collection<String> allTableNames, Collection<String> beforeTableNames, Collection<TableRouteUnit> filterRouteUnits);
}
