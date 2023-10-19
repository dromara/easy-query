package com.easy.query.core.func;

import com.easy.query.core.func.def.DistinctDefaultSQLFunction;

/**
 * create time 2023/10/19 01:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class DistinctDefaultSettingImpl implements DistinctOrDefaultSelector {
    private final DistinctDefaultSQLFunction distinctDefaultSQLFunction;

    public DistinctDefaultSettingImpl(DistinctDefaultSQLFunction distinctDefaultSQLFunction) {

        this.distinctDefaultSQLFunction = distinctDefaultSQLFunction;
    }

    @Override
    public DistinctOrDefaultSelector distinct() {
        distinctDefaultSQLFunction.distinct();
        return this;
    }

    @Override
    public DistinctOrDefaultSelector valueOrDefault(Object value) {
        distinctDefaultSQLFunction.valueOrDefault(value);
        return this;
    }
}
