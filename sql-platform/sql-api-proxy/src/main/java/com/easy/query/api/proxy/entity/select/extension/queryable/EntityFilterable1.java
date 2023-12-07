package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.exception.EasyQueryMultiPrimaryKeyException;
import com.easy.query.core.exception.EasyQueryNoPrimaryKeyException;
import com.easy.query.core.exception.EasyQueryWhereInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLPredicateExpression;

import java.util.Collection;

/**
 * create time 2023/12/4 10:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityFilterable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
    /**
     * 构建where条件
     * where(o->o.id().eq().and(o.id().eq()))
     * where(o->Predicate.and(
     *      o.id().eq(),
     *      o.id().eq(),
     *      Predicate.or(
     *          o.id().eq(),
     *          o.id().eq()
     *      )
     * ))
     * @param whereExpression where表达式
     * @return 返回当前查询queryable
     */
    default EntityQueryable<T1Proxy, T1> where(SQLFuncExpression1<T1Proxy, SQLPredicateExpression> whereExpression) {
        return where(true, whereExpression);
    }

    /**
     * 构建where条件
     * where(boolean, o->o.id().eq().and(o.id().eq()))
     * where(boolean, o->Predicate.and(
     *      o.id().eq(),
     *      o.id().eq(),
     *      Predicate.or(
     *          o.id().eq(),
     *          o.id().eq()
     *      )
     * ))
     * @param condition 是否要添加后续的表达式,true:表示要添加,false表示不添加
     * @param whereExpression where表达式
     * @return 返回当前查询queryable
     */
    EntityQueryable<T1Proxy, T1> where(boolean condition, SQLFuncExpression1<T1Proxy, SQLPredicateExpression> whereExpression);


    /**
     * 根据主键查询
     * where(id)
     * @param id 主键
     * @return 链式表达式
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException @description 无主键或者多主键报错
     */

    default EntityQueryable<T1Proxy, T1> whereById(Object id) {
        return whereById(true, id);
    }

    /**
     * 根据主键查询
     * where(boolean，id)
     * @param condition 是否要添加主键查询到当前表达式 true:要添加,false:要添加
     * @param id where表达式
     * @return 返回当前查询queryable
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException @description 无主键或者多主键报错
     */

    EntityQueryable<T1Proxy, T1> whereById(boolean condition, Object id);

    /**
     * 根据主键集合进行查询
     * where(Arrays.asList("1","2","3"))
     * @param ids 主键集合
     * @param <TProperty> 主键类型
     * @return 返回当前查询queryable
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException
     */

    default <TProperty> EntityQueryable<T1Proxy, T1> whereByIds(Collection<TProperty> ids) {
        return whereByIds(true, ids);
    }

    /**
     * 根据主键集合查询
     * where(boolean,Arrays.asList("1","2","3"))
     * @param condition   是否添加该条件到表达式 true:添加,false:不添加
     * @param ids         主键集合
     * @param <TProperty> 主键类型
     * @return 当前链式表达式
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException
     */
    <TProperty> EntityQueryable<T1Proxy, T1> whereByIds(boolean condition, Collection<TProperty> ids);

    /**
     * 使用对象进行查询 配合{@link com.easy.query.core.annotation.EasyWhereCondition} 设置条件对应的表和条件值
     * whereObject(request)
     * @param object 查询对象
     * @return
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
     * @return
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配,无法获取
     */
    EntityQueryable<T1Proxy, T1> whereObject(boolean condition, Object object);
}
