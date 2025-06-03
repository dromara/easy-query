package com.easy.query.test.common;

import com.easy.query.core.expression.builder.core.AnyValueFilter;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.builder.core.ValueFilterFactory;

/**
 * create time 2024/12/8 18:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyValueFilterFactory implements ValueFilterFactory {
    @Override
    public ValueFilter getQueryValueFilter() {
        return NotNullOrEmptyValueFilter.DEFAULT;
    }

    @Override
    public ValueFilter getInsertValueFilter() {
        return AnyValueFilter.DEFAULT;
    }

    @Override
    public ValueFilter getExecuteValueFilter() {
        return AnyValueFilter.DEFAULT;
    }

    @Override
    public ValueFilter getUpdateValueFilter() {
        return AnyValueFilter.DEFAULT;
    }

    @Override
    public ValueFilter getDeleteValueFilter() {
        return AnyValueFilter.DEFAULT;
    }
}
