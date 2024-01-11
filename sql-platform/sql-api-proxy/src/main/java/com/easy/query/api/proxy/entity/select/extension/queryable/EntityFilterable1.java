package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.exception.EasyQueryMultiPrimaryKeyException;
import com.easy.query.core.exception.EasyQueryNoPrimaryKeyException;
import com.easy.query.core.exception.EasyQueryWhereInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;

/**
 * create time 2023/12/4 10:10
 * 对象条件过滤
 *
 * @author xuejiaming
 */
public interface EntityFilterable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
    /**
     * 构建where条件
     *
     * <blockquote><pre>
     * {@code
     * where(o->o.id().eq("123")) //id = '123'
     * where(o->o.id().eq(o.name())) //id = name
     * where(o->o.id().nullDefault("123").eq("123")) //ifnull(id,'123') = '123'
     * where(o->{
     *     o.id().eq("123"); //id = '123' AND name like '%456%'
     *     o.name().like("456")
     * })
     *
     * where(o->{   //(id = '123' OR name like '%456%')
     *     o.or(()->{
     *         o.id().eq("123");
     *         o.name().like("456")
     *     })
     * })
     *    }
     * </pre></blockquote>
     * @param whereExpression where表达式
     * @return 当前链式表达式
     */
    default EntityQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression) {
        return where(true, whereExpression);
    }
//    EntityQueryable<T1Proxy,T1> where1(SQLExpression1<T1Proxy> whereExpression);

    /**
     * 构建where条件,第一个参数condition表示是否需要生成后续条件
     *
     * <blockquote><pre>
     * {@code
     * where(true,o->o.id().eq("123")) //id = '123' 因为condition:true所以会生成 [id = '123']
     * where(false,o->o.id().eq(o.name())) //id = name 因为condition:false将不会生成条件 [id = name]
     *    }
     * </pre></blockquote>
     * @param condition 是否要添加后续的表达式,true:表示要添加,false表示不添加
     * @param whereExpression where表达式
     * @return 当前链式表达式
     */
    EntityQueryable<T1Proxy, T1> where(boolean condition, SQLExpression1<T1Proxy> whereExpression);


    /**
     * 根据主键查询,对象T1不可以是多主键或者无主键模式仅单主键模式才能支持
     * where(id)
     * @param id 主键
     * @return 链式表达式
     * @throws EasyQueryNoPrimaryKeyException 当前对象无主键报错
     * @throws EasyQueryMultiPrimaryKeyException 当前对象存在多个主键报错
     */
    default EntityQueryable<T1Proxy, T1> whereById(Object id) {
        return whereById(true, id);
    }

    /**
     * 根据主键查询,对象T1不可以是多主键或者无主键模式仅单主键模式才能支持
     * where(boolean，id)
     * @param condition 是否要添加主键查询到当前表达式 true:要添加,false:要添加
     * @param id where表达式
     * @return 当前链式表达式
     * @throws EasyQueryNoPrimaryKeyException 当前对象无主键报错
     * @throws EasyQueryMultiPrimaryKeyException 当前对象存在多个主键报错
     */
    EntityQueryable<T1Proxy, T1> whereById(boolean condition, Object id);

    /**
     * 根据主键集合进行查询,对象T1不可以是多主键或者无主键模式仅单主键模式才能支持
     * where(Arrays.asList("1","2","3"))
     * @param ids 主键集合
     * @param <TProperty> 主键类型
     * @return 当前链式表达式
     * @throws EasyQueryNoPrimaryKeyException 当前对象无主键报错
     * @throws EasyQueryMultiPrimaryKeyException 当前对象存在多个主键报错
     */
    default <TProperty> EntityQueryable<T1Proxy, T1> whereByIds(Collection<TProperty> ids) {
        return whereByIds(true, ids);
    }

    /**
     * 根据主键集合进行查询,对象T1不可以是多主键或者无主键模式仅单主键模式才能支持
     * 当当前表达式为多表查询时查询主表主键
     * where(boolean,Arrays.asList("1","2","3"))
     * @param condition   是否添加该条件到表达式 true:添加,false:不添加
     * @param ids         主键集合
     * @param <TProperty> 主键类型
     * @return 当前链式表达式
     * @throws EasyQueryNoPrimaryKeyException 当前对象无主键报错
     * @throws EasyQueryMultiPrimaryKeyException 当前对象存在多个主键报错
     */
    <TProperty> EntityQueryable<T1Proxy, T1> whereByIds(boolean condition, Collection<TProperty> ids);

    /**
     * 使用对象进行查询 配合{@link com.easy.query.core.annotation.EasyWhereCondition} 设置条件对应的表和条件值
     * whereObject(request)
     * @param object 查询对象
     * @return 当前链式表达式
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配
     */
    default EntityQueryable<T1Proxy, T1> whereObject(Object object) {
        return whereObject(true, object);
    }

    /**
     * 使用对象进行查询 配合{@link com.easy.query.core.annotation.EasyWhereCondition} 设置条件对应的表和条件值
     * whereObject(boolean,request)
     * @param condition 是否要使用当前的对象查询方法 true:使用,false:不使用
     * @param object 查询对象
     * @return 当前链式表达式
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配,无法获取
     */
    EntityQueryable<T1Proxy, T1> whereObject(boolean condition, Object object);
}
