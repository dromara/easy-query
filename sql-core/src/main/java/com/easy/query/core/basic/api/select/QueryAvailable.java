package com.easy.query.core.basic.api.select;

/**
 * create time 2023/10/7 15:06
 * 文件说明
 *
 * @author xuejiaming
 */
public interface QueryAvailable<T> {
    /**
     * 当前查询对象的字节信息
     *
     * @return 当前查询的对象字节
     */
    Class<T> queryClass();
}
