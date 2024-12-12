package com.easy.query.core.expression.sql.include;

/**
 * create time 2024/12/12 16:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class CountElement implements Countable {
    private final Object element;
    private int count = 0;

    public CountElement(Object element){
        this.element = element;
    }
    @Override
    public Object getElement() {
        return element;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void increment() {
        count++;
    }

    @Override
    public void decrement() {
        count--;
    }
}
