package com.easy.query.api.proxy.core.base;


import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.io.Serializable;
import java.util.function.Function;

/**
 * create time 2023/6/21 16:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableProxy<T extends TableProxy<T,TClass>,TClass> extends Serializable {

    TableAvailable getTable();

    T create(Function<Class<TClass>,T> creator);

}
