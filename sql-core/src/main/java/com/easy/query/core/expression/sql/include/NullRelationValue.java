package com.easy.query.core.expression.sql.include;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * create time 2024/10/16 17:14
 * 当且仅当selfProperty为长度为1或者空(主键情况)的数组时才会使用当前对象
 * 比如:selfProperty=["id"]那么value为id的值
 *
 * @author xuejiaming
 */
public class NullRelationValue implements RelationValue {

    @Override
    public List<Object> getValues() {
        return null;
    }

    @Override
    public boolean isNull() {
        return true;
    }


    @Override
    public String toString() {
        return "NullRelationValue{" +
                "value=" + null +
                '}';
    }
}
