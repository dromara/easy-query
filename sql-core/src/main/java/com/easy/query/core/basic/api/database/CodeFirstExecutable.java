package com.easy.query.core.basic.api.database;

import java.util.function.Consumer;

/**
 * create time 2025/1/26 08:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface CodeFirstExecutable {
    /**
     * 使用环境事务自动提交
     * @param consumer 自动提交前的打印
     */
    void executeWithEnvTransaction(Consumer<CodeFirstCommandArg> consumer);

    /**
     * 使用eq内置事务可以手动控制是否提交事务
     * @param consumer
     */
    void executeWithTransaction(Consumer<CodeFirstCommandTxArg> consumer);
}
