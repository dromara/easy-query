package com.easy.query.test.entity;

/**
 * create time 2023/12/11 14:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyCat implements MyFunc<MyCat>{
    @Override
    public Object getFuncClass() {
        return MyDog.class;
    }

}
