package com.easy.query.core.sharding.router.table;

/**
 * create time 2023/5/16 09:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableUnit {
    String getDataSourceName();

    String getLogicTableName();

    String getActualTableName();
}
