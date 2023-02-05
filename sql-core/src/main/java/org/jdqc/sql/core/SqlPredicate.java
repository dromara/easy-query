package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SqlPredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 15:38
 * @Created by xuejiaming
 */
public interface SqlPredicate<T1,Children extends SqlPredicate<T1,Children>> extends Predicate<Children>,OnPredicate<Children> {
}
