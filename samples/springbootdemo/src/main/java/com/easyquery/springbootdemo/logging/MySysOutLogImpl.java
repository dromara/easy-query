package com.easyquery.springbootdemo.logging;

import com.easy.query.core.logging.Log;

/**
 * create time 2023/5/23 12:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySysOutLogImpl implements Log {
    /**
     * 必须要实现一个构造函数,传入单个字符串string参数
     * @param clazz
     */
    public MySysOutLogImpl(String clazz){
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
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public void error(String s, Throwable e) {
        System.out.println("----开始----");
        System.err.println(s);
        e.printStackTrace(System.err);
        System.out.println("----结束----");
    }

    @Override
    public void error(String s) {
        System.out.println("----开始----");
        System.err.println(s);
        System.out.println("----结束----");
    }

    @Override
    public void debug(String s) {
        System.out.println("----开始----");
        System.out.println(s);
        System.out.println("----结束----");
    }

    @Override
    public void trace(String s) {
        System.out.println("----开始----");
        System.out.println(s);
        System.out.println("----结束----");
    }

    @Override
    public void info(String s) {
        System.out.println("----开始----");
        System.out.println(s);
        System.out.println("----结束----");
    }

    @Override
    public void warn(String s) {
        System.out.println("----开始----");
        System.out.println(s);
        System.out.println("----结束----");
    }
}
