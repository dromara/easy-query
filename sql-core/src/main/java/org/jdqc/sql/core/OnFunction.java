package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: OnFunction.java
 * @Description: 文件说明
 * @Date: 2023/2/4 22:31
 * @Created by xuejiaming
 */
@FunctionalInterface
public interface OnFunction<T> {

    T apply(T wrapper);
}