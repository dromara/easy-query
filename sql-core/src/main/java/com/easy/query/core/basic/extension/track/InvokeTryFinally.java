package com.easy.query.core.basic.extension.track;/**
  * create time 2023/8/27 11:09
  * 文件说明
  * @author xuejiaming
  */
public interface InvokeTryFinally {
    /**
     * 当前上下文尝试开启追踪,以开启将不在开启，需要和{@link #release()}成对使用
     */
    void begin();
    /**
     * 当前上下文清空，需要和{@link #begin()}成对使用
     */
    void release();
}
