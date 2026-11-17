package com.easy.query.core.basic.api.database;

/**
 * create time 2025/1/26 08:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface CommandTxArg {

    String getSQL();
    void commit();
}
