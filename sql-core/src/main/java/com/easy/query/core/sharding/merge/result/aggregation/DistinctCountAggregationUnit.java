package com.easy.query.core.sharding.merge.result.aggregation;

import com.easy.query.core.util.EasyCollectionUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * create time 2023/4/28 17:28
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DistinctCountAggregationUnit implements AggregationUnit{
    private Set<Comparable<?>> values=new HashSet<>();
    @Override
    public void merge(List<Comparable<?>> values) {
        if(EasyCollectionUtil.isEmpty(values)||values.get(0)==null){
            return;
        }
        values.add(values.get(0));
    }

    @Override
    public Comparable<?> getResult() {
        return values.size();
    }
}
