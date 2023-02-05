package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: Select2.java
 * @Description: 文件说明
 * @Date: 2023/2/4 22:25
 * @Created by xuejiaming
 */
public interface Select2<T1, T2>extends Select0{
    <T3> Select3<T1, T2,T3> leftJoin(Class<T3> t3Class, Property<OnCondition2, OnCondition2> on);
}
