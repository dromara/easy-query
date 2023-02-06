package org.jdqc.sql.core.abstraction.lambda;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: OnFunction.java
 * @Description: 文件说明
 * @Date: 2023/2/4 22:31
 * @Created by xuejiaming
 */
@FunctionalInterface
public interface SqlExpression3<T1,T2,T3> {

    void apply(T1 p1,T2 p2,T3 p3);
}