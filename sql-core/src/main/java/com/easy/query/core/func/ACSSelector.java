package com.easy.query.core.func;

/**
 * create time 2023/10/19 01:02
 * AVG COUNT SUM选择器
 *
 * @author xuejiaming
 */
public interface ACSSelector {
    /**
     * 函数支持distinct
     * @param dist 是否使用distinct
     * @return
     */
    ACSSelector distinct(boolean dist);

    /**
     * 函数支持null default
     * @param value
     * @return
     */
    ACSSelector nullDefault(Object value);
}
