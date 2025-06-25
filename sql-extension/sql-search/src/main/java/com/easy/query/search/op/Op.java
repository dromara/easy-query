package com.easy.query.search.op;

/**
 * 运算符接口
 *
 * @author bkbits
 */
public interface Op {
    /**
     * @return 获取符号名，该符号名将用于-op符号参数匹配
     */
    String getName();

    /**
     * 执行过滤操作
     *
     * @param context 上下文对象
     */
    void filter(FilterContext context);
}
