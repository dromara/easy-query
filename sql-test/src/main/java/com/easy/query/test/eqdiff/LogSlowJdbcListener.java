package com.easy.query.test.eqdiff;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecuteBeforeArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;

/**
 * create time 2024/8/27 10:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class LogSlowJdbcListener implements JdbcExecutorListener {
    private final NamedConfig namedConfig;

    public LogSlowJdbcListener(NamedConfig namedConfig){
        this.namedConfig = namedConfig;
    }
    @Override
    public boolean enable() {
        return true;
    }

    @Override
    public void onExecuteBefore(JdbcExecuteBeforeArg arg) {

    }

    @Override
    public void onExecuteAfter(JdbcExecuteAfterArg afterArg) {
        System.out.println(namedConfig.getName());
    }
}
