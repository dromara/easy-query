package com.easy.query.test.inject;

/**
 * create time 2023/5/6 10:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class BeanCurrent1 {
    private final BeanCurrent2 beanCurrent2;

    public BeanCurrent1(BeanCurrent2 beanCurrent2){

        this.beanCurrent2 = beanCurrent2;
    }
}
