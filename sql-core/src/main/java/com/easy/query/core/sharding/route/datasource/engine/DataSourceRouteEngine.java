package com.easy.query.core.sharding.route.datasource.engine;

import com.easy.query.core.expression.executor.parser.descriptor.TableParseDescriptor;
import com.easy.query.core.sharding.route.descriptor.RouteDescriptor;

/**
 * create time 2023/4/11 13:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceRouteEngine {
    DataSourceRouteResult route(TableParseDescriptor tableParseDescriptor);
}
