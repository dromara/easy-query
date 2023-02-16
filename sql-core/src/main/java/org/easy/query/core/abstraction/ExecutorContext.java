package org.easy.query.core.abstraction;

/**
 * @FileName: ExecutorContext.java
 * @Description: 文件说明
 * @Date: 2023/2/16 22:51
 * @Created by xuejiaming
 */
public class ExecutorContext {

    private final EasyQueryRuntimeContext runtimeContext;

    public ExecutorContext(EasyQueryRuntimeContext runtimeContext){

        this.runtimeContext = runtimeContext;
    }
    public static ExecutorContext create(EasyQueryRuntimeContext runtimeContext){
        return new ExecutorContext(runtimeContext);
    }
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }
}
