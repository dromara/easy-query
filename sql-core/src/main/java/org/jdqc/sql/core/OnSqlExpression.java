package org.jdqc.sql.core;

import java.io.Serializable;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SqlExpression.java
 * @Description: 文件说明
 * @Date: 2023/2/5 08:35
 * @Created by xuejiaming
 */
@FunctionalInterface
public interface OnSqlExpression<T> extends Serializable {
    T apply(T t);
}
