package com.easy.query.core.proxy;

/**
 * create time 2023/12/2 18:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAsPropColumnExpression {

    <TProperty> PropTypeColumn<TProperty> asPropColumn(Class<TProperty> propType);
}
