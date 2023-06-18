package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.basic.api.select.ClientQueryable;

import java.util.function.Function;

/**
 * create time 2023/6/18 10:43
 * 文件说明
 *
 * @author xuejiaming
 */
public interface NavigateInclude<T1> {
    default<TProperty> NavigateInclude<T1> with(String property){
        return with(property,q->q);
    }
    <TProperty> NavigateInclude<T1> with(String property, Function<ClientQueryable<TProperty>, ClientQueryable<TProperty>> func);

    default  <T2> NavigateInclude<T2> then(NavigateInclude<T2> sub) {
        return sub;
    }
}
