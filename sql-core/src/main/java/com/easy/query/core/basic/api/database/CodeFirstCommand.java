package com.easy.query.core.basic.api.database;

import java.util.function.Consumer;

/**
 * create time 2025/1/26 08:39
 * code first 命令
 *
 * @author xuejiaming
 */
public interface CodeFirstCommand {

    /**
     * 直接执行命令
     */
    void commands(Consumer<CommandTxArg> consumer);
}
