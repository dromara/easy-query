package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.segment.SQLSegment;

import java.util.function.Function;

/**
 * create time 2024/5/19 20:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLSegment implements SQLSegment {
    private final Function<ToSQLContext, String> toSQLFunction;

    public DefaultSQLSegment(Function<ToSQLContext,String> toSQLFunction){

        this.toSQLFunction = toSQLFunction;
    }
    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return toSQLFunction.apply(toSQLContext);
    }
}
