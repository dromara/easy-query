package org.easy.query.core.abstraction;

import org.easy.query.core.basic.api.delete.EntityDeletable;
import org.easy.query.core.basic.api.delete.ExpressionDeletable;
import org.easy.query.core.basic.api.insert.Insertable;
import org.easy.query.core.basic.api.select.Queryable;
import org.easy.query.core.basic.api.select.Queryable2;
import org.easy.query.core.basic.api.select.Queryable3;
import org.easy.query.core.basic.api.select.Queryable4;
import org.easy.query.core.basic.api.update.EntityUpdatable;
import org.easy.query.core.basic.api.update.ExpressionUpdatable;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.query.SqlEntityInsertExpression;
import org.easy.query.core.query.SqlEntityQueryExpression;

import java.util.Collection;

/**
 * @FileName: EasyQueryableFactory.java
 * @Description: 文件说明
 * @Date: 2023/3/3 13:28
 * @Created by xuejiaming
 */
public interface EasySqlApiFactory {
    default <T> Queryable<T> createQueryable(Class<T> clazz, EasyQueryRuntimeContext runtimeContext) {
        return createQueryable(clazz, runtimeContext, "t");
    }

    <T> Queryable<T> createQueryable(Class<T> clazz, EasyQueryRuntimeContext runtimeContext, String alias);

    <T> Queryable<T> cloneQueryable(Queryable<T> source);

    <T> Queryable<T> createQueryable(Class<T> clazz, SqlEntityQueryExpression sqlEntityExpression);

    //    <T1,T2>Queryable2<T1,T2> createQueryable2(Class<T1> t1Class,Class<T2> t2Class,MultiTableTypeEnum selectTableInfoType,EasyQueryRuntimeContext runtimeContext);
    <T1, T2> Queryable2<T1, T2> createQueryable2(Class<T1> t1Class, Class<T2> t2Class, MultiTableTypeEnum selectTableInfoType, SqlEntityQueryExpression sqlEntityExpression);
    <T1, T2> Queryable2<T1, T2> createQueryable2(Class<T1> t1Class, Queryable<T2> joinQueryable, MultiTableTypeEnum selectTableInfoType, SqlEntityQueryExpression sqlEntityExpression);
    <T1, T2,T3> Queryable3<T1, T2,T3> createQueryable3(Class<T1> t1Class, Class<T2> t2Class,Class<T3> t3Class, MultiTableTypeEnum selectTableInfoType, SqlEntityQueryExpression sqlEntityExpression);
    <T1, T2,T3> Queryable3<T1, T2,T3> createQueryable3(Class<T1> t1Class,Class<T2> t2Class, Queryable<T3> joinQueryable, MultiTableTypeEnum selectTableInfoType, SqlEntityQueryExpression sqlEntityExpression);
    <T1, T2,T3,T4> Queryable4<T1, T2,T3,T4> createQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, MultiTableTypeEnum selectTableInfoType, SqlEntityQueryExpression sqlEntityExpression);
    <T1, T2,T3,T4> Queryable4<T1, T2,T3,T4> createQueryable4(Class<T1> t1Class,Class<T2> t2Class,Class<T3> t3Class, Queryable<T4> joinQueryable, MultiTableTypeEnum selectTableInfoType, SqlEntityQueryExpression sqlEntityExpression);

    <T> Insertable<T> createInsertable(Class<T> clazz, EasyQueryRuntimeContext runtimeContext, String alias);

    /**
     * 创建一个空的insert接口
     *
     * @param runtimeContext
     * @param alias
     * @param <T>
     * @return
     */
    <T> Insertable<T> createEmptyInsertable(EasyQueryRuntimeContext runtimeContext, String alias);

    <T> Insertable<T> createInsertable(Class<T> clazz, SqlEntityInsertExpression sqlEntityInsertExpression);

    <T> EntityUpdatable<T> createEmptyEntityUpdatable();

    <T> EntityUpdatable<T> createEntityUpdatable(T entity, EasyQueryRuntimeContext runtimeContext, String alias);

    <T> EntityUpdatable<T> createEntityUpdatable(Collection<T> entities, EasyQueryRuntimeContext runtimeContext, String alias);

    <T> ExpressionUpdatable<T> createExpressionUpdatable(Class<T> entityClass, EasyQueryRuntimeContext runtimeContext, String alias);

    <T> EntityDeletable<T> createEmptyEntityDeletable();
    <T> EntityDeletable<T> createEntityDeletable(T entity, EasyQueryRuntimeContext runtimeContext, String alias);
    <T> EntityDeletable<T> createEntityDeletable(Collection<T> entities, EasyQueryRuntimeContext runtimeContext, String alias);

    <T> ExpressionDeletable<T> createExpressionDeletable(Class<T> entityClass, EasyQueryRuntimeContext runtimeContext, String alias);
}
