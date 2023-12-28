package com.easy.query.core.proxy;

/**
 * create time 2023/12/18 22:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PropTypeColumn<TProperty> extends PropTypeSetColumn<TProperty>{
    @Override
    <TR> PropTypeColumn<TR> setPropertyType(Class<TR> clazz);
}
