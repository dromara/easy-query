package com.easy.query.test.listener;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import lombok.Data;

/**
 * create time 2023/11/11 23:15
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class ListenerContext {
    private JdbcExecuteAfterArg jdbcExecuteAfterArg;
}
