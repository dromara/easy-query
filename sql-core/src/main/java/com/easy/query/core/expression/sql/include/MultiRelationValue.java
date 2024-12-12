package com.easy.query.core.expression.sql.include;

import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;
import java.util.Objects;

/**
 * create time 2024/10/16 17:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class MultiRelationValue implements RelationValue {
    private final List<Object> values;

    public MultiRelationValue(List<Object> values){
        this.values = values;
    }

    @Override
    public List<Object> getValues() {
        return values;
    }
    @Override
    public boolean isNull(){
        return EasyCollectionUtil.all(values, Objects::isNull);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiRelationValue that = (MultiRelationValue) o;
        return Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(values);
    }

    @Override
    public String toString() {
        return "MultiRelationValue{" +
                "values=" + values +
                '}';
    }
}
