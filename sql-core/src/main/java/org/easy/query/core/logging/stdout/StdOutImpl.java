package org.easy.query.core.logging.stdout;

import org.easy.query.core.logging.Log;

/**
 * @FileName: StdOutImpl.java
 * @Description: 文件说明
 * @Date: 2023/3/10 13:46
 * @Created by xuejiaming
 */
public class StdOutImpl implements Log {
    public StdOutImpl(String clazz) {
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void error(String s, Throwable e) {
        System.err.println(s);
        e.printStackTrace(System.err);
    }

    @Override
    public void error(String s) {
        System.err.println(s);
    }

    @Override
    public void debug(String s) {
        System.out.println(s);
    }

    @Override
    public void trace(String s) {
        System.out.println(s);
    }

    @Override
    public void warn(String s) {
        System.out.println(s);
    }
}
