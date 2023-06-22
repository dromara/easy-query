package com.easy.query.api.proxy.core;

import com.easy.query.api.proxy.core.base.TableProxy;

/**
 * create time 2023/6/21 22:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnProxy<TProxy extends TableProxy<TProxy,T1>,T1> extends TableProxy<TProxy,T1>, SQLWhereColumn<TProxy> {
}
