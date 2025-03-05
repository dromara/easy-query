package com.easy.query.core.basic.api.database;

import com.easy.query.core.basic.jdbc.tx.Transaction;

import java.util.List;
import java.util.function.Consumer;

/**
 * create time 2025/1/26 08:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface CodeFirstCommandTxArg {

    String getSQL();
    void commit();

    void rollback();
}
