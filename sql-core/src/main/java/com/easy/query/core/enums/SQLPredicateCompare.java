package com.easy.query.core.enums;

/**
 * create time 2023/2/28 21:05
 * 文件说明
 * @author xuejiaming
 */
public interface SQLPredicateCompare {
    String getSQL();
    SQLPredicateCompare toReverse();
}
