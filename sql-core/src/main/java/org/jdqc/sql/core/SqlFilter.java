package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SqlFilter.java
 * @Description: 文件说明
 * @Date: 2023/2/5 22:45
 * @Created by xuejiaming
 */
public interface SqlFilter<Children> {
    default Children skip(int skip){
        return skip(true,skip);
    }
    Children skip(boolean condition,int skip);

    default  Children take(int take){
        return take(true,take);
    }
    Children take(boolean condition,int take);
}
