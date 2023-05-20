package com.easy.query.core.basic.jdbc.executor.internal.merge.result.aggregation;

import com.easy.query.core.util.EasyClassUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * create time 2023/4/28 21:03
 * 文件说明
 *
 * @author xuejiaming
 */
public final class AccumulationAggregationUnit implements AggregationUnit {

    private  Class<?> requiredType;
    private BigDecimal result;

    @Override
    public void merge(final List<Comparable<?>> values) {
        if (null == values) {
            return;
        }
        Comparable<?> firstValue = values.get(0);
        if(null == firstValue){
            return;
        }
        if (null == result) {
            result = new BigDecimal("0");
        }
//        if(requiredType==null){
//            requiredType=firstValue.getClass();
//        }
        result = result.add(new BigDecimal(firstValue.toString()));
    }

    @Override
    public Comparable<?> getResult() {
        return result;
//        if(requiredType==null){
//        }
//        Object newValue = EasyClassUtil.convertValueToRequiredType(result, requiredType);
//        return (Comparable<?>) newValue;
    }
}
