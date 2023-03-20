package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;

/**
 * @FileName: ExecutorContext.java
 * @Description: 文件说明
 * @Date: 2023/2/16 22:51
 * @Created by xuejiaming
 */
public class ExecutorContext {

    private final EasyQueryRuntimeContext runtimeContext;
    private final boolean tracking;

    public ExecutorContext(EasyQueryRuntimeContext runtimeContext){
        this(runtimeContext,false);
    }
    public ExecutorContext(EasyQueryRuntimeContext runtimeContext,boolean tracking){
        this.runtimeContext = runtimeContext;
        this.tracking = tracking;
    }
    public static ExecutorContext create(EasyQueryRuntimeContext runtimeContext){
        return new ExecutorContext(runtimeContext);
    }
    public static ExecutorContext create(EasyQueryRuntimeContext runtimeContext,boolean tracking){
        return new ExecutorContext(runtimeContext,tracking);
    }
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    public boolean isTracking() {
        return tracking;
    }
}
