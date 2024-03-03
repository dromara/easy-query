package com.easy.query.api.proxy.key;

/**
 * create time 2024/3/2 19:37
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MapKey<TPropType> {
     String getName();
    Class<TPropType> getPropType();
}
