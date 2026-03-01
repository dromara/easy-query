package com.easy.query.core.proxy.extension.functions.type.impl.as;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.type.IgnoreValueConverterTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.DateTimeTypeExpressionImpl;

import java.util.function.Function;

/**
 * create time 2026/3/1 19:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class AsDateTimeTypeExpressionImpl<TProperty>  extends DateTimeTypeExpressionImpl<TProperty> implements IgnoreValueConverterTypeExpression {
    public AsDateTimeTypeExpressionImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property, Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        super(entitySQLContext, table, property, func, propType);
    }
}
