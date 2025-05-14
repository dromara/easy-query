package com.easy.query.core.logging;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.logging.nologging.NoLoggingImpl;
import com.easy.query.core.logging.stdout.StdOutImpl;

import java.lang.reflect.Constructor;

/**
 * @FileName: LogFactory.java
 * @Description: 文件说明
 * create time 2023/3/10 13:45
 * @author xuejiaming
 */
public class LogFactory {

    /**
     * Marker to be used by logging implementations that support markers.
     */
    public static final String MARKER = "EASYQUERY";

    private static Constructor<? extends Log> logConstructor;

    static {
        tryImplementation(LogFactory::useNoLogging);
    }

    private LogFactory() {
        // disable construction
    }

    public static Log getLog(Class<?> clazz) {
        return getLog(clazz.getName());
    }

    public static Log getLog(String logger) {
        try {
            return logConstructor.newInstance(logger);
        } catch (Throwable t) {
            throw new EasyQueryException("Error creating logger for logger " + logger + ".  Cause: " + t, t);
        }
    }

    public static synchronized void useCustomLogging(Class<? extends Log> clazz) {
        setImplementation(clazz);
    }

    public static synchronized void useStdOutLogging() {
        setImplementation(StdOutImpl.class);
    }

    public static synchronized void useNoLogging() {
        setImplementation(NoLoggingImpl.class);
    }

    private static void tryImplementation(Runnable runnable) {
        if (logConstructor == null) {
            try {
                runnable.run();
            } catch (Throwable t) {
                // ignore
            }
        }
    }

    private static void setImplementation(Class<? extends Log> implClass) {
        try {
            Constructor<? extends Log> candidate = implClass.getConstructor(String.class);
            Log log = candidate.newInstance(LogFactory.class.getName());
            if (log.isDebugEnabled()) {
                log.debug("Logging initialized using '" + implClass + "' adapter.");
            }
            logConstructor = candidate;
        } catch (Throwable t) {
            throw new EasyQueryException("Error setting Log implementation.  Cause: " + t, t);
        }
    }
}
