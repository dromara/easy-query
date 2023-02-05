package org.jdqc.sql.core;

import java.util.List;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: Select0.java
 * @Description: 文件说明
 * @Date: 2023/2/4 22:07
 * @Created by xuejiaming
 */
public interface Select0<TSelect> {
    TResult firstOrDefault();
    boolean Any();
    int count();
    List<TResult> ToList();
    TSelect skip(int skip);
    TSelect take(int take);
//     TSelect leftJoin(Class t1Clazz,SFunction<OnCondition2, OnCondition2> on);
//     TSelect rightJoin(Class t1Clazz,SFunction<OnCondition2, OnCondition2> on);
//     TSelect innerJoin(Class t1Clazz,SFunction<OnCondition2, OnCondition2> on);
////    TSelect rightJoin();
////    TSelect innerJoin();
//   <T> TSelect eq(boolean condition,SFunction<T,?> column,Object val);
//
//    default <T>  TSelect eq(SFunction<T,?> column,Object val){
//        return eq(true,column,val);
//    }
}
