package com.easy.query.core.common;

/**
 * create time 2024/8/12 13:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class MapValue {
    private final Object currentValue;
    private final Object predicateValue;

    public MapValue(Object currentValue, Object predicateValue){

        this.currentValue = currentValue;
        this.predicateValue = predicateValue;
    }

    public Object getCurrentValue() {
        return currentValue;
    }

    public Object getPredicateValue() {
        return predicateValue;
    }
}
