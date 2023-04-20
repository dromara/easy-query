package com.easy.query.core.sharding.rule.table;

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
    Collection<String> beforeFilterTableName(Collection<String> allTableNames);
    Collection<TableRouteUnit> afterFilterTableName(Collection<String> allTableNames, Collection<String> beforeTableNames, Collection<TableRouteUnit> filterRouteUnits);
}
