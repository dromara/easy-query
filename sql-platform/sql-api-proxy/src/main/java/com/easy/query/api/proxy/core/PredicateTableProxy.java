package com.easy.query.api.proxy.core;

import com.easy.query.api.proxy.core.base.TableProxy;

/**
 * create time 2023/6/21 21:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PredicateTableProxy<T extends TableProxy<T,TClass>,TClass> {
    T create();
}
