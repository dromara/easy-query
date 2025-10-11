package com.easy.query.api.proxy.extension.window;

import com.easy.query.core.proxy.PropTypeColumn;

/**
 * create time 2025/10/11 10:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class NextOffset<TProperty> {
    private final int offset;
    private  PropTypeColumn<TProperty> defaultColumn;
    private  TProperty defaultValue;

    public NextOffset(int offset) {
        this.offset = offset;
    }

    public boolean isNext() {
        return offset >= 0;
    }

    public int getOffset() {
        return Math.abs(offset);
    }

    public PropTypeColumn<TProperty> getDefaultColumn() {
        return defaultColumn;
    }

    public void setDefaultColumn(PropTypeColumn<TProperty> defaultColumn) {
        this.defaultColumn = defaultColumn;
    }

    public TProperty getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(TProperty defaultValue) {
        this.defaultValue = defaultValue;
    }

    public static <TProp> NextOffset<TProp> of(int offset){
        return of(offset, null);
    }
    public static <TProp> NextOffset<TProp> of(int offset, PropTypeColumn<TProp> defaultColumn){
        NextOffset<TProp> nextOffset = new NextOffset<>(offset);
        nextOffset.setDefaultColumn(defaultColumn);
        return nextOffset;
    }
    public static <TProp> NextOffset<TProp> of(int offset, TProp defaultValue){
        NextOffset<TProp> nextOffset = new NextOffset<>(offset);
        nextOffset.setDefaultValue(defaultValue);
        return nextOffset;
    }
}
