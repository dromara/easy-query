package com.easy.query.core.exception;

/**
 * 框架执行过程中出现的错误
 * @FileName: JDQCException.java
 * @Description: 文件说明
 * @Date: 2023/2/7 12:36
 * @author xuejiaming
 */
public class EasyQueryShardingRouteExecuteMoreException extends RuntimeException{
    public EasyQueryShardingRouteExecuteMoreException(String msg){
        super(msg);
    }
    public EasyQueryShardingRouteExecuteMoreException(Throwable e){
        super(e);
    }

    public EasyQueryShardingRouteExecuteMoreException(String msg, Throwable e){
        super(msg,e);
    }

}
