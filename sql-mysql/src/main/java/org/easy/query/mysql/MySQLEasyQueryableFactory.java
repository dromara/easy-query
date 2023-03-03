package org.easy.query.mysql;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.abstraction.EasyQueryableFactory;
import org.easy.query.core.basic.api.select.Queryable;
import org.easy.query.core.basic.api.select.Queryable2;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.expression.context.SelectContext;
import org.easy.query.mysql.base.MySQLQueryable;
import org.easy.query.mysql.base.MySQLQueryable2;

/**
 * @FileName: MySQLEasyQueryableFactory.java
 * @Description: 文件说明
 * @Date: 2023/3/3 13:32
 * @Created by xuejiaming
 */
public class MySQLEasyQueryableFactory implements EasyQueryableFactory {
    @Override
    public <T> Queryable<T> createQueryable(Class<T> clazz, EasyQueryRuntimeContext runtimeContext,String alias) {
        return new MySQLQueryable<>(clazz,new SelectContext(runtimeContext,alias));
    }

    @Override
    public <T> Queryable<T> createQueryable(Class<T> clazz, SelectContext selectContext) {
        return new MySQLQueryable<>(clazz,new SelectContext(selectContext));
    }
//
//    @Override
//    public <T1, T2> Queryable2<T1, T2> createQueryable2(Class<T1> t1Class, Class<T2> t2Class, MultiTableTypeEnum selectTableInfoType, EasyQueryRuntimeContext runtimeContext) {
//        return new MySQLQueryable2<>(t1Class,t2Class,new SelectContext(runtimeContext),selectTableInfoType);
//    }

    @Override
    public <T1, T2> Queryable2<T1, T2> createQueryable2(Class<T1> t1Class, Class<T2> t2Class, MultiTableTypeEnum selectTableInfoType, SelectContext selectContext) {
        return new MySQLQueryable2<>(t1Class,t2Class,new SelectContext(selectContext),selectTableInfoType);
    }
}
