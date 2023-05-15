package com.easy.query.core.basic.jdbc.executor.internal.merge.result.aggregation;

import com.easy.query.core.util.EasyCollectionUtil;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * create time 2023/4/28 17:28
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DistinctSumAggregationUnit implements AggregationUnit{
    private BigDecimal result;
    private Set<Comparable<?>> values=new HashSet<>();
    @Override
    public void merge(List<Comparable<?>> values) {
        if(EasyCollectionUtil.isEmpty(values)||values.get(0)==null){
            return;
        }
        if(this.values.add(values.get(0))){
            if(null==result){
                result=new BigDecimal("0");
            }
            result = result.add(new BigDecimal(values.get(0).toString()));
        }
    }

    @Override
    public Comparable<?> getResult() {
        return result;
    }
}
