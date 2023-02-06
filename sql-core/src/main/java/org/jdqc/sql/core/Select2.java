package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 * 数据库查询
 * @FileName: Select.java
 * @Description: 文件说明
 * @Date: 2023/2/4 23:32
 * @Created by xuejiaming
 */
public interface Select2<T1, TR> extends SqlPredicate2<T1, Select2<T1, TR>>
        , SqlFetch<TR>
        , SqlFilter<Select2<T1, TR>>
        , SqlSelector2<Select2<T1, TR>,T1, TR>
        , SqlJoin<T1, TR> {
}
