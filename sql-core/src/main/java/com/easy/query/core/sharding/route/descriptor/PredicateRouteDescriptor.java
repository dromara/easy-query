package com.easy.query.core.sharding.route.descriptor;

import com.easy.query.core.expression.segment.condition.PredicateSegment;

import java.util.List;

/**
 * create time 2023/5/18 16:04
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PredicateRouteDescriptor extends RouteDescriptor{
    List<PredicateSegment> getPredicates();
}
