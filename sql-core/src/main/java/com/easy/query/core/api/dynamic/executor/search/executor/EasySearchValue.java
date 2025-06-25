package com.easy.query.core.api.dynamic.executor.search.executor;

/**
 * 条件值
 *
 * @author bkbits
 */
class EasySearchValue {
    private final int index;
    private final Object value;

    public EasySearchValue(int index, Object value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public Object getValue() {
        return value;
    }
}
