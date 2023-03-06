package org.easy.query.core.abstraction;

import org.easy.query.core.basic.api.insert.Insertable;
import org.easy.query.core.basic.api.select.Queryable;
import org.easy.query.core.basic.api.select.Queryable2;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.query.SqlEntityInsertExpression;
import org.easy.query.core.query.SqlEntityQueryExpression;

/**
 * @FileName: EasyQueryableFactory.java
 * @Description: 文件说明
 * @Date: 2023/3/3 13:28
 * @Created by xuejiaming
 */
public interface EasySqlApiFactory {
   default  <T> Queryable<T> createQueryable(Class<T> clazz,EasyQueryRuntimeContext runtimeContext){
       return createQueryable(clazz,runtimeContext,"t");
   }
    <T> Queryable<T> createQueryable(Class<T> clazz,EasyQueryRuntimeContext runtimeContext,String alias);
    <T> Queryable<T> cloneQueryable(Queryable<T> source);
    <T> Queryable<T> createQueryable(Class<T> clazz, SqlEntityQueryExpression sqlEntityExpression);

//    <T1,T2>Queryable2<T1,T2> createQueryable2(Class<T1> t1Class,Class<T2> t2Class,MultiTableTypeEnum selectTableInfoType,EasyQueryRuntimeContext runtimeContext);
    <T1,T2>Queryable2<T1,T2> createQueryable2(Class<T1> t1Class,Class<T2> t2Class,MultiTableTypeEnum selectTableInfoType, SqlEntityQueryExpression sqlEntityExpression);

    <T> Insertable<T> createInsertable(Class<T> clazz,EasyQueryRuntimeContext runtimeContext,String alias);

    /**
     * 创建一个空的insert接口
     * @param runtimeContext
     * @param alias
     * @return
     * @param <T>
     */
    <T> Insertable<T> createEmptyInsertable(EasyQueryRuntimeContext runtimeContext,String alias);
    <T> Insertable<T> createInsertable(Class<T> clazz, SqlEntityInsertExpression sqlEntityInsertExpression);
}
