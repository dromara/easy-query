package com.easy.query.api.proxy.core;

/**
 * create time 2023/6/21 21:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLWhereColumn<TProxy> {

     TProxy eq(Object value);
     TProxy like(Object value);
     TProxy isNull();
     TProxy isNotNull();
}
