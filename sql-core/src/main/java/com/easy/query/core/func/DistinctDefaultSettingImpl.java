package com.easy.query.core.func;

import com.easy.query.core.func.def.DistinctDefaultSQLFunction;

/**
 * create time 2023/10/19 01:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class DistinctDefaultSettingImpl implements ACSSelector {
    private final DistinctDefaultSQLFunction distinctDefaultSQLFunction;

    public DistinctDefaultSettingImpl(DistinctDefaultSQLFunction distinctDefaultSQLFunction) {

        this.distinctDefaultSQLFunction = distinctDefaultSQLFunction;
    }

    @Override
    public ACSSelector distinct(boolean dist) {
        distinctDefaultSQLFunction.distinct(dist);
        return this;
    }

    @Override
    public ACSSelector nullDefault(Object value) {
        distinctDefaultSQLFunction.nullDefault(value);
        return this;
    }
}
