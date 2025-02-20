package com.easy.query.test.common;

import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;
import java.util.Objects;

/**
 * create time 2024/10/16 17:14
 * 当且仅当selfProperty为长度大于1的数组时才会使用当前对象
 * 比如:selfProperty=["id","username"]那么values[0]为id的值,values[1]为username的值
 *
 * @author xuejiaming
 */
public class MyMultiRelationValue implements RelationValue {
    protected final List<Object> values;

    public MyMultiRelationValue(List<Object> values) {
        this.values = values;
    }

    @Override
    public List<Object> getValues() {
        return values;
    }

    /**
     * 当且仅当values中的有任意元素是null时返回true
     * 如果你认为例子中的id或者username有其他不符合就可以直接忽略可以使用重写该类来替换掉默认行为
     *
     * @return
     */
    @Override
    public boolean isNull() {
        return EasyCollectionUtil.any(values, o -> Objects.isNull(o) || Objects.equals("-", o));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyMultiRelationValue that = (MyMultiRelationValue) o;
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
