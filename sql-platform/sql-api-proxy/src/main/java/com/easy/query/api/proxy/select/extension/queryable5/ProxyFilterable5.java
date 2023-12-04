package com.easy.query.api.proxy.select.extension.queryable5;

import com.easy.query.api.proxy.select.ProxyQueryable5;
import com.easy.query.api.proxy.select.extension.queryable5.sql.MultiProxyFilter5;
import com.easy.query.api.proxy.select.extension.queryable5.sql.impl.MultiProxyFilter5Impl;
import com.easy.query.core.exception.EasyQueryMultiPrimaryKeyException;
import com.easy.query.core.exception.EasyQueryNoPrimaryKeyException;
import com.easy.query.core.exception.EasyQueryWhereInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLPredicate;
import com.easy.query.core.proxy.sql.Predicate;
import com.easy.query.core.util.EasyArrayUtil;

import java.util.Collection;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyFilterable5<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> extends ClientProxyQueryable5Available<T1, T2, T3, T4, T5>, ProxyQueryable5Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> {

    /**
     * 构建where条件
     * where(table.id().eq(...),table.name().eq(...))
     * @param sqlPredicates where表达式
     * @return 返回当前查询queryable
     */
    default ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> where(SQLPredicate... sqlPredicates) {
        return where(true, sqlPredicates);
    }
    /**
     * 构建where条件
     * where(table.id().eq(...),table.name().eq(...))
     * @param condition 是否要添加后续的表达式,true:表示要添加,false表示不添加
     * @param sqlPredicates where表达式
     * @return 返回当前查询queryable
     */
    default ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> where(boolean condition, SQLPredicate... sqlPredicates){
        if (condition) {
            if(EasyArrayUtil.isNotEmpty(sqlPredicates)){
                getClientQueryable5().where((wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5) -> {
                    SQLPredicate predicate = Predicate.and(sqlPredicates);
                    predicate.accept(wherePredicate1.getFilter());
                });
            }
        }
        return getQueryable5();
    }
    default ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> where(SQLExpression1<MultiProxyFilter5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> whereExpression) {
        return where(true, whereExpression);
    }

    default ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> where(boolean condition, SQLExpression1<MultiProxyFilter5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> whereExpression) {
        if (condition) {
            getClientQueryable5().where((t, t1, t2, t3, t4) -> {
                whereExpression.apply(new MultiProxyFilter5Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy()));
            });
        }
        return getQueryable5();
    }
    /**
     * 根据主键查询
     * where(id)
     * @param id 主键
     * @return 链式表达式
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException @description 无主键或者多主键报错
     */

    default ProxyQueryable5<T1Proxy,T1,T2Proxy,T2,T3Proxy,T3,T4Proxy,T4,T5Proxy,T5> whereById(Object id) {
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

    default ProxyQueryable5<T1Proxy,T1,T2Proxy,T2,T3Proxy,T3,T4Proxy,T4,T5Proxy,T5> whereById(boolean condition, Object id){

        if (condition) {
            getClientQueryable5().whereById(id);
        }
        return getQueryable5();
    }

    /**
     * 根据主键集合进行查询
     * where(Arrays.asList("1","2","3"))
     * @param ids 主键集合
     * @param <TProperty> 主键类型
     * @return 返回当前查询queryable
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException
     */

    default <TProperty> ProxyQueryable5<T1Proxy,T1,T2Proxy,T2,T3Proxy,T3,T4Proxy,T4,T5Proxy,T5> whereByIds(Collection<TProperty> ids) {
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
    default <TProperty> ProxyQueryable5<T1Proxy,T1,T2Proxy,T2,T3Proxy,T3,T4Proxy,T4,T5Proxy,T5> whereByIds(boolean condition, Collection<TProperty> ids){

        if (condition) {
            getClientQueryable5().whereByIds(ids);
        }
        return getQueryable5();
    }

    /**
     * 使用对象进行查询 配合{@link com.easy.query.core.annotation.EasyWhereCondition} 设置条件对应的表和条件值
     * whereObject(request)
     * @param object 查询对象
     * @return
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配
     */
    default ProxyQueryable5<T1Proxy,T1,T2Proxy,T2,T3Proxy,T3,T4Proxy,T4,T5Proxy,T5> whereObject(Object object) {
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
    default ProxyQueryable5<T1Proxy,T1,T2Proxy,T2,T3Proxy,T3,T4Proxy,T4,T5Proxy,T5> whereObject(boolean condition, Object object){

        if (condition) {
            getClientQueryable5().whereObject(object);
        }
        return getQueryable5();
    }
}
