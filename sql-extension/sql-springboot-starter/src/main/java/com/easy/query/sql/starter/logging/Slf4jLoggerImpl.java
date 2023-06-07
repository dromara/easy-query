package com.easy.query.sql.starter.logging;

import com.easy.query.core.logging.Log;
import org.slf4j.Logger;

/**
 * @author xuejiaming
 * @FileName: Slf4jLoggerImpl.java
 * @Description: 文件说明
 * @Date: 2023/3/11 14:05
 */
public class Slf4jLoggerImpl implements Log {

    private final Logger log;

    public Slf4jLoggerImpl(Logger logger) {
        log = logger;
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    @Override
    public void error(String s, Throwable e) {
        log.error(s, e);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void debug(String s) {
        log.debug(s);
    }

    @Override
    public void trace(String s) {
        log.trace(s);
    }

    @Override
    public void info(String s) {
        log.info(s);
    }

    @Override
    public void warn(String s) {
        log.warn(s);
    }

}
