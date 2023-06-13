package com.easy.query.core.sharding.router.datasource.engine;

import com.easy.query.core.expression.executor.parser.descriptor.TableParseDescriptor;

/**
 * create time 2023/4/11 13:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceRouteEngine {
    DataSourceRouteResult route(TableParseDescriptor tableParseDescriptor);
}
