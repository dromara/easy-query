package com.easy.query.core.basic.api.database;

import java.util.function.Consumer;

/**
 * create time 2025/1/26 08:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface CodeFirstExecutable {
    void execute(Consumer<CodeFirstCommandArg> consumer);
    void executeWithTransaction(Consumer<CodeFirstCommandTxArg> consumer);
}
