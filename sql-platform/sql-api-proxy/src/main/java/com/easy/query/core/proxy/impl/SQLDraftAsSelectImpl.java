package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.function.BiConsumer;

/**
 * create time 2023/12/19 23:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLDraftAsSelectImpl<T> implements PropTypeColumn<T> {


    private final BiConsumer<String, AsSelector> selectorConsumer;
    private Class<?> propType;

    public SQLDraftAsSelectImpl(BiConsumer<String, AsSelector> selectorConsumer) {
        this(selectorConsumer, Object.class);
    }

    public SQLDraftAsSelectImpl(BiConsumer<String, AsSelector> selectorConsumer, Class<?> propType) {
        this.selectorConsumer = selectorConsumer;

        this.propType = propType;
    }

    @Override
    public SQLSelectAsExpression as(String propertyAlias) {
        return new SQLSelectAsImpl(s -> {
            throw new UnsupportedOperationException();
        }, s -> {
            selectorConsumer.accept(propertyAlias, s);
        }, s -> {
            throw new UnsupportedOperationException();
        });
    }

    @Override
    public TableAvailable getTable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<?> getPropertyType() {
        return propType;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TR> void _setPropertyType(Class<TR> clazz) {
        this.propType = clazz;
    }

    @Override
    public <TR> PropTypeColumn<TR> asAnyType(Class<TR> clazz) {
        _setPropertyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }

}
