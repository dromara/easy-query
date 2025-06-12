package com.easy.query.core.expression.builder;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.builder.core.SelectorColumn;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.func.SQLFunction;

import java.util.function.Function;

/**
 * create time 2023/6/22 20:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Selector extends SelectorColumn<Selector>,SQLNative<Selector>, RuntimeContextAvailable {

    EntityQueryExpressionBuilder getEntityQueryExpressionBuilder();
    /**
     * 快速选择之前group的列,不需要重新再写一遍
     *
     * @param index
     * @return
     */
    Selector groupKeys(int index);

    Selector columnKeys(TableAvailable table);
    Selector columnAs(TableAvailable table, String property,String propertyAlias);
    Selector columnAs(TableAvailable table, String property, String propertyAlias, Function<?, ?> valueConverter);
    Selector columnFixedAs(TableAvailable table, String property,String propertyAlias);

    Selector columnFunc(TableAvailable table, SQLFunction sqlFunction, String propertyAlias);


    Selector columnAll(TableAvailable table);
}
