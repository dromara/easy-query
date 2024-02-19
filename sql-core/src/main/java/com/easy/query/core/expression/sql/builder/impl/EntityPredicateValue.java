package com.easy.query.core.expression.sql.builder.impl;

/**
 * create time 2024/2/19 20:59
 * 文件说明
 *
 * @author xuejiaming
 */
public final class EntityPredicateValue {
    private final boolean track;
    private final Object predicateValue;

    public EntityPredicateValue(boolean track, Object predicateValue){

        this.track = track;
        this.predicateValue = predicateValue;
    }

    public boolean isTrack() {
        return track;
    }

    public Object getPredicateValue() {
        return predicateValue;
    }
}
