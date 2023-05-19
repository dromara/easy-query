package com.easy.query.core.sharding.route.descriptor;

import com.easy.query.core.expression.executor.parser.descriptor.TableParseDescriptor;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/5/19 08:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RouteDescriptorFactory {
    RouteDescriptor createRouteDescriptor(TableAvailable table, TableParseDescriptor tableParseDescriptor);
}
