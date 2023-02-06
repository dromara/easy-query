package org.jdqc.sql.core;

import java.util.List;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SqlResult.java
 * @Description: 文件说明
 * @Date: 2023/2/5 22:29
 * @Created by xuejiaming
 */
public interface SqlFetch<TR> {
    int count();
    boolean any();
    TR firstOrNull();
    List<TR> toList();
    String toSql();
}
