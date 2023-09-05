package com.easy.query.api4kt.sql;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.impl.EasyKtQueryable;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import kotlin.reflect.KProperty1;

import java.util.Collection;

/**
 * create time 2023/6/18 11:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtNavigateInclude<T1> {
    NavigateInclude<T1> getNavigateInclude();
   default  <TProperty> KtQueryable<TProperty> one(KProperty1<? super T1,TProperty> navigate){
       return one(navigate,null);
   }
   default  <TProperty> KtQueryable<TProperty> one(KProperty1<? super T1,TProperty> navigate,Integer groupSize){
       ClientQueryable<TProperty> clientQueryable = getNavigateInclude().<TProperty>with(EasyKtLambdaUtil.getPropertyName(navigate),groupSize);
       return new EasyKtQueryable<>(clientQueryable);
   }
   default  <TProperty> KtQueryable<TProperty> many(KProperty1<? super T1, Collection<TProperty>> navigate){
       return many(navigate,null);
   }
   default  <TProperty> KtQueryable<TProperty> many(KProperty1<? super T1, Collection<TProperty>> navigate,Integer groupSize){
       ClientQueryable<TProperty> clientQueryable = getNavigateInclude().<TProperty>with(EasyKtLambdaUtil.getPropertyName(navigate),groupSize);
       return new EasyKtQueryable<>(clientQueryable);
   }
}
