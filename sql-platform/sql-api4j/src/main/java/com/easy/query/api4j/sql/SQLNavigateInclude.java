package com.easy.query.api4j.sql;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.impl.EasyQueryable;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;

import java.util.Collection;

/**
 * create time 2023/6/18 11:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNavigateInclude<T1> {
    NavigateInclude<T1> getNavigateInclude();
   default  <TProperty> Queryable<TProperty> one(Property<T1,TProperty> navigate){
       ClientQueryable<TProperty> clientQueryable = getNavigateInclude().<TProperty>with(EasyLambdaUtil.getPropertyName(navigate));
       return new EasyQueryable<>(clientQueryable);
   }
   default  <TProperty> Queryable<TProperty> many(Property<T1, Collection<TProperty>> navigate){
       ClientQueryable<TProperty> clientQueryable = getNavigateInclude().<TProperty>with(EasyLambdaUtil.getPropertyName(navigate));
       return new EasyQueryable<>(clientQueryable);
   }

}
