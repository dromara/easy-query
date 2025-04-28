package com.easy.query.core.proxy.columns;

import com.easy.query.core.proxy.extension.functions.ColumnStringFunctionAvailable;
import com.easy.query.core.proxy.predicate.DSLColumnContainsPredicate;
import com.easy.query.core.proxy.predicate.DSLColumnPropTypeContainsPredicate;
import com.easy.query.core.proxy.predicate.DSLStringAssertPredicate;

/**
 * create time 2023/12/24 00:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLStringColumn<TProxy, TProperty> extends SQLObjectColumn<TProxy,TProperty>,
        DSLStringAssertPredicate<TProperty>,
        DSLColumnContainsPredicate,
        DSLColumnPropTypeContainsPredicate,
        ColumnStringFunctionAvailable<TProperty> {
}
