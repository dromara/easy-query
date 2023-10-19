package com.easy.query.core.func;

/**
 * create time 2023/10/19 01:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DistinctOrDefaultSelector {
    DistinctOrDefaultSelector distinct();
    DistinctOrDefaultSelector valueOrDefault(Object value);
}
