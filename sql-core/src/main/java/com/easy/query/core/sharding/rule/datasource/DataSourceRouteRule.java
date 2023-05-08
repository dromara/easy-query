package com.easy.query.core.sharding.rule.datasource;

import com.easy.query.core.sharding.rule.RouteRule;
import com.easy.query.core.sharding.rule.RouteRuleFilter;

import java.util.Collection;

/**
 * create time 2023/4/19 13:00
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceRouteRule<T> extends RouteRule, RouteRuleFilter {
    Collection<String> beforeFilterDataSource(Collection<String> allDataSources);
    Collection<String> afterFilterDataSource(Collection<String> allDataSources, Collection<String> beforeDataSources, Collection<String> filterDataSources);

}
