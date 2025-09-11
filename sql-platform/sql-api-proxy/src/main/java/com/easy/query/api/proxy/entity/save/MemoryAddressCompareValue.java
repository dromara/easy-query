package com.easy.query.api.proxy.entity.save;

import java.util.Objects;

/**
 * create time 2025/9/11 08:51
 * 用来解决相同对象属性同一个但是其实是两个对象的问题
 * 如果直接用map或者set则无法区分是否是同一个因为可能该对象的所有属性都是null那么重写equals和hashcode后会导致map和set
 * 认为他们是同一个对象
 *
 * @author xuejiaming
 */
public class MemoryAddressCompareValue {
    private final Object entity;

    public MemoryAddressCompareValue(Object entity) {
        this.entity = entity;
    }

    public Object getEntity() {
        return entity;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        MemoryAddressCompareValue that = (MemoryAddressCompareValue) object;
        return entity == that.entity;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(entity);
    }
}
