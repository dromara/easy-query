package com.easy.query.core.expression.sql.fill;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;

import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * create time 2023/7/18 22:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class FillExpression {
    private final SQLFuncExpression1<FillParams, Query<?>> fillSQLFuncExpression;
    private final Class<?> fillFromEntityClass;
    private final boolean many;
    private final String targetProperty;
    private final String selfProperty;
    private  BiConsumer<Object, Collection<?>> produceMany;
    private  BiConsumer<Object, ?> produceOne;


    public FillExpression(Class<?> fillFromEntityClass,boolean many,String targetProperty, String selfProperty, SQLFuncExpression1<FillParams, Query<?>> fillSQLFuncExpression){
        this.fillFromEntityClass = fillFromEntityClass;
        this.many = many;
        this.targetProperty = targetProperty;
        this.selfProperty = selfProperty;

        this.fillSQLFuncExpression = fillSQLFuncExpression;
    }

    public SQLFuncExpression1<FillParams, Query<?>> getFillSQLFuncExpression() {
        return fillSQLFuncExpression;
    }

    public boolean isMany() {
        return many;
    }

    public String getTargetProperty() {
        return targetProperty;
    }

    public String getSelfProperty() {
        return selfProperty;
    }

    public BiConsumer<Object, Collection<?>> getProduceMany() {
        return produceMany;
    }

    public void setProduceMany(BiConsumer<Object, Collection<?>> produceMany) {
        this.produceMany = produceMany;
    }

    public BiConsumer<Object, ?> getProduceOne() {
        return produceOne;
    }

    public void setProduceOne(BiConsumer<Object, ?> produceOne) {
        this.produceOne = produceOne;
    }

    public Class<?> getFillFromEntityClass() {
        return fillFromEntityClass;
    }
}
