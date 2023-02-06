package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 * 数据库查询
 * @FileName: Select.java
 * @Description: 文件说明
 * @Date: 2023/2/4 23:32
 * @Created by xuejiaming
 */
public interface Select<T1, TR> extends SqlPredicate<T1, Select<T1, TR>>
        , SqlFetch<TR>
        , SqlFilter<Select<T1, TR>>
        , SqlSelector<Select<T1, TR>,T1, TR>
        , SqlJoin<T1, TR> {
}
