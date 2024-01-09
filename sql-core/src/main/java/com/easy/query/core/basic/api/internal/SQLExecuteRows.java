package com.easy.query.core.basic.api.internal;

/**
 * @Description: 文件说明
 * @Date: 2023/3/17 17:34
 * @author xuejiaming
 */
public interface SQLExecuteRows {
    /**
     * 返回受影响行数
     * 具体参考jdbc驱动是否有实现批处理返回函数
     * 如果是很大的负数那么表示jdbc不知道具体返回行数是多少所以返回SUCCESS_NO_INFO (-2)的值,表示命令为处理成功，但受影响的行数为未知
     * @return
     */
    long executeRows();
}
