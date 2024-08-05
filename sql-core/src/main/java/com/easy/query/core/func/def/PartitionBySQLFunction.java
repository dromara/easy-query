package com.easy.query.core.func.def;

import com.easy.query.core.func.SQLFunction;

/**
 * create time 2024/8/4 15:40
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PartitionBySQLFunction extends SQLFunction{
    PartitionBySQLFunction addOrder(SQLFunction sqlFunction);
}
