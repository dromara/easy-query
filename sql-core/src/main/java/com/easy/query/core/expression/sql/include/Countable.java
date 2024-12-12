package com.easy.query.core.expression.sql.include;

/**
 * create time 2024/12/12 16:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Countable {
    Object getElement();

    int getCount();
    void increment();
    void decrement();
}
