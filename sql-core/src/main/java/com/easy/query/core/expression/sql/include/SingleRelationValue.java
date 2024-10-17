package com.easy.query.core.expression.sql.include;

import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * create time 2024/10/16 17:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class SingleRelationValue implements RelationValue {
    private final Object value;

    public SingleRelationValue(Object value) {
        this.value = value;
    }

    @Override
    public List<Object> getValues() {
        return Collections.singletonList(value);
    }

    @Override
    public boolean isNull() {
        return Objects.isNull(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleRelationValue that = (SingleRelationValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
