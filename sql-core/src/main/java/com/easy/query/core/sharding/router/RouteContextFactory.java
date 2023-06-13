package com.easy.query.core.sharding.router;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;

/**
 * create time 2023/4/20 13:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RouteContextFactory {
    RouteContext createRouteContext(PrepareParseResult prepareParseResult);
}
