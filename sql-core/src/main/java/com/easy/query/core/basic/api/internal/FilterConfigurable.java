package com.easy.query.core.basic.api.internal;

import com.easy.query.core.expression.builder.core.ValueFilter;

/**
 * create time 2023/8/19 15:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface FilterConfigurable<TChain> {
    /**
     * 除了subQuery参数其余都会进入这个判断比较包括collection和array
     * @param valueFilter
     * @return
     */
    TChain filterConfigure(ValueFilter valueFilter);
}
