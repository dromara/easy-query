package com.easy.query.core.expression.many2group;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

import java.util.List;
import java.util.Objects;

/**
 * create time 2025/3/8 09:47
 * 文件说明
 *
 * @author xuejiaming
 */
public class ManyGroupJoinProjectKey {
    private final String key;

    public ManyGroupJoinProjectKey(String sql, String parameters, String methodName) {
        this.key = String.format("sql:%s,parameters:%s,method:%s", sql, parameters, methodName);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ManyGroupJoinProjectKey that = (ManyGroupJoinProjectKey) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }
}
