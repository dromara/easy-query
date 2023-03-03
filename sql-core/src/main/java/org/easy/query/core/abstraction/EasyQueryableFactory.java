package org.easy.query.core.abstraction;

import org.easy.query.core.basic.api.select.Queryable;
import org.easy.query.core.basic.api.select.Queryable2;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.expression.context.SelectContext;

/**
 * @FileName: EasyQueryableFactory.java
 * @Description: 文件说明
 * @Date: 2023/3/3 13:28
 * @Created by xuejiaming
 */
public interface EasyQueryableFactory {
   default  <T> Queryable<T> createQueryable(Class<T> clazz,EasyQueryRuntimeContext runtimeContext){
       return createQueryable(clazz,runtimeContext,null);
   }
    <T> Queryable<T> createQueryable(Class<T> clazz,EasyQueryRuntimeContext runtimeContext,String alias);
    <T> Queryable<T> createQueryable(Class<T> clazz, SelectContext selectContext);

//    <T1,T2>Queryable2<T1,T2> createQueryable2(Class<T1> t1Class,Class<T2> t2Class,MultiTableTypeEnum selectTableInfoType,EasyQueryRuntimeContext runtimeContext);
    <T1,T2>Queryable2<T1,T2> createQueryable2(Class<T1> t1Class,Class<T2> t2Class,MultiTableTypeEnum selectTableInfoType, SelectContext selectContext);
}
