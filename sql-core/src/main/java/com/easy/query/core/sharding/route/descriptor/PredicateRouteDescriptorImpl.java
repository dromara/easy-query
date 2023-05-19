package com.easy.query.core.sharding.route.descriptor;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

import java.util.List;

/**
 * create time 2023/5/18 16:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class PredicateRouteDescriptorImpl implements PredicateRouteDescriptor{
    private final TableAvailable table;
    private final List<PredicateSegment> predicates;

    public PredicateRouteDescriptorImpl(TableAvailable table, List<PredicateSegment> predicates){

        this.table = table;
        this.predicates = predicates;
    }
    @Override
    public List<PredicateSegment> getPredicates() {
        return predicates;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }
}
