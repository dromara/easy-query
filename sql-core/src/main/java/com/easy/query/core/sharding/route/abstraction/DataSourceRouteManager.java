package com.easy.query.core.sharding.route.abstraction;

import java.util.List;

/**
 * create time 2023/4/5 22:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceRouteManager {
    List<String> routeTo(Class<?> entityType,DataSourceRouteParams dataSourceRouteParams);
}
