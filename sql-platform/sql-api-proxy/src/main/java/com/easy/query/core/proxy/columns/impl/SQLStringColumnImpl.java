package com.easy.query.core.proxy.columns.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.columns.SQLStringColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableStringChainExpression;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

import java.util.function.Function;

/**
 * create time 2023/12/24 00:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLStringColumnImpl<TProxy, TProperty> extends SQLColumnImpl<TProxy, TProperty> implements SQLStringColumn<TProxy, TProperty> {
    public SQLStringColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property, Class<TProperty> propType) {
        super(entitySQLContext, table, property, propType);
    }
}
