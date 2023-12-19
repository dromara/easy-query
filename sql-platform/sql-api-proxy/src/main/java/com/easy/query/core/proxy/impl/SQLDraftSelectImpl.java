package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
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
public class SQLDraftSelectImpl implements PropTypeColumn<Object> {


    private final BiConsumer<String, Selector> selectorConsumer;
    private final BiConsumer<String, AsSelector> selectorAsConsumer;
    private Class<?> propType;

    public SQLDraftSelectImpl(BiConsumer<String,Selector> selectorConsumer,BiConsumer<String, AsSelector> selectorAsConsumer){
        this.selectorConsumer = selectorConsumer;
        this.selectorAsConsumer = selectorAsConsumer;

        this.propType = Object.class;
    }

    @Override
    public SQLSelectAsExpression as(String propertyAlias) {
        return new SQLSelectAsImpl(s -> {
            selectorConsumer.accept(propertyAlias,s);
        }, s -> {
            selectorAsConsumer.accept(propertyAlias,s);
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
    public Class<?> propertyType() {
        return propType;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TR> PropTypeColumn<TR> castType(Class<TR> clazz) {
        this.propType=clazz;
        return EasyObjectUtil.typeCastNullable(this);
    }
}
