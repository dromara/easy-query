package com.easy.query.api4j.sql;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.impl.EasyQueryable;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.util.EasyLambdaUtil;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/6/18 11:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNavigateInclude<T1> {
    NavigateInclude<T1> getNavigateInclude();
   default  <TProperty> SQLNavigateInclude<T1> withOne(Property<T1,TProperty> navigate, Function<Queryable<TProperty>,Queryable<TProperty>> func){
       getNavigateInclude().<TProperty>with(EasyLambdaUtil.getPropertyName(navigate), q->func.apply(new EasyQueryable<>(q)).getEntityQueryable());
       return this;
   }
   default  <TProperty> SQLNavigateInclude<T1> withMany(Property<T1, Collection<TProperty>> navigate){
       getNavigateInclude().<TProperty>with(EasyLambdaUtil.getPropertyName(navigate));
       return this;
   }
   default  <TProperty> SQLNavigateInclude<T1> withMany(Property<T1, Collection<TProperty>> navigate, Function<Queryable<TProperty>,Queryable<TProperty>> func){
       getNavigateInclude().<TProperty>with(EasyLambdaUtil.getPropertyName(navigate), q->func.apply(new EasyQueryable<>(q)).getEntityQueryable());
       return this;
   }

    default  <T2> SQLNavigateInclude<T2> then(SQLNavigateInclude<T2> sub) {
        return sub;
    }
}
