package com.easy.query.core.expression.builder.core;

/**
 * create time 2024/12/8 18:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ValueFilterFactory {
    /**
     * 查询默认条件过滤
     * @return
     */
    ValueFilter getQueryValueFilter();

    /**
     * insert表达式条件过滤(目前无效)
     * @return
     */
    ValueFilter getInsertValueFilter();

    /**
     * 执行原生sql 执行原生sql无论是insert update 还是delete都是这个
     * @return
     */
    ValueFilter getExecuteValueFilter();

    /**
     * 更新表达式条件过滤
     * @return
     */
    ValueFilter getUpdateValueFilter();

    /**
     * 删除表达式条件过滤
     * @return
     */
    ValueFilter getDeleteValueFilter();
}
