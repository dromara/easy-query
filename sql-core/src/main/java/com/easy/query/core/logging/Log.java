package com.easy.query.core.logging;

/**
 * @FileName: Log.java
 * @Description: 文件说明
 * @Date: 2023/3/10 13:45
 * @Created by xuejiaming
 */
public interface Log {

    boolean isDebugEnabled();

    boolean isTraceEnabled();

    void error(String s, Throwable e);

    void error(String s);

    void debug(String s);

    void trace(String s);

    void warn(String s);

}
