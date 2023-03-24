package com.easy.query.core.logging.nologging;

import com.easy.query.core.logging.Log;

/**
 * @FileName: NoLoggingImpl.java
 * @Description: 文件说明
 * @Date: 2023/3/10 13:52
 * @author xuejiaming
 */
public class NoLoggingImpl implements Log {

    public NoLoggingImpl(String clazz) {
        // Do Nothing
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void error(String s, Throwable e) {
        // Do Nothing
    }

    @Override
    public void error(String s) {
        // Do Nothing
    }

    @Override
    public void debug(String s) {
        // Do Nothing
    }

    @Override
    public void trace(String s) {
        // Do Nothing
    }

    @Override
    public void warn(String s) {
        // Do Nothing
    }
}
