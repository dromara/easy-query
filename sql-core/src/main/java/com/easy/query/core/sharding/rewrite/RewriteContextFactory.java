package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.sharding.route.RouteContext;

/**
 * create time 2023/4/20 13:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RewriteContextFactory {
    RewriteContext createRewriteContext(RouteContext routeContext);
}
