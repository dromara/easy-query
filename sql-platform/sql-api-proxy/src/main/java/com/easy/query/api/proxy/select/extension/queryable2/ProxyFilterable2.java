package com.easy.query.api.proxy.select.extension.queryable2;

import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.api.proxy.select.extension.queryable2.sql.MultiProxyFilter2;
import com.easy.query.api.proxy.select.extension.queryable2.sql.impl.MultiProxyFilter2Impl;
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
public interface ProxyFilterable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientProxyQueryable2Available<T1, T2>, ProxyQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    /**
     * 构建where条件
     * where(table.id().eq(...),table.name().eq(...))
     * @param sqlPredicates where表达式
     * @return 返回当前查询queryable
     */
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> where(SQLPredicate... sqlPredicates) {
        return where(true, sqlPredicates);
    }
    /**
     * 构建where条件
     * where(table.id().eq(...),table.name().eq(...))
     * @param condition 是否要添加后续的表达式,true:表示要添加,false表示不添加
     * @param sqlPredicates where表达式
     * @return 返回当前查询queryable
     */
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> where(boolean condition, SQLPredicate... sqlPredicates){
        if (condition) {
            if(EasyArrayUtil.isNotEmpty(sqlPredicates)){
                getClientQueryable2().where((wherePredicate1, wherePredicate2) -> {
                    SQLPredicate predicate = Predicate.and(sqlPredicates);
                    predicate.accept(wherePredicate1.getFilter());
                });
            }
        }
        return getQueryable2();
    }
//    @Deprecated
//    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> where1(SQLFuncExpression1<MultiProxyFilter2<T1Proxy, T2Proxy>,SQLPredicate> whereExpression) {
//
//        getClientQueryable2().where((t,t1) -> {
//            MultiProxyFilter2Impl<T1Proxy, T1,T2Proxy,T2> t1ProxyT1MultiProxyFilter1 = new MultiProxyFilter2Impl<>(t.getFilter(), get1Proxy(),get2Proxy());
//            SQLPredicate apply = whereExpression.apply(t1ProxyT1MultiProxyFilter1);
//            apply.accept(t.getFilter());
//        });
//        return getQueryable2();
//    }
    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> where(SQLExpression1<MultiProxyFilter2<T1Proxy, T2Proxy>> whereExpression) {
        return where(true,whereExpression);
    }

    default ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> where(boolean condition, SQLExpression1<MultiProxyFilter2<T1Proxy, T2Proxy>> whereExpression) {
        if (condition) {
            getClientQueryable2().where((wherePredicate1, wherePredicate2) -> {
                whereExpression.apply(new MultiProxyFilter2Impl<>(wherePredicate2.getFilter(), get1Proxy(), get2Proxy()));
            });
        }
        return getQueryable2();
    }


    /**
     * 根据主键查询
     * where(id)
     * @param id 主键
     * @return 链式表达式
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException @description 无主键或者多主键报错
     */

    default ProxyQueryable2<T1Proxy,T1,T2Proxy,T2> whereById(Object id) {
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

   default ProxyQueryable2<T1Proxy,T1,T2Proxy,T2> whereById(boolean condition, Object id){

       if (condition) {
           getClientQueryable2().whereById(id);
       }
       return getQueryable2();
   }

    /**
     * 根据主键集合进行查询
     * where(Arrays.asList("1","2","3"))
     * @param ids 主键集合
     * @param <TProperty> 主键类型
     * @return 返回当前查询queryable
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException
     */

    default <TProperty> ProxyQueryable2<T1Proxy,T1,T2Proxy,T2> whereByIds(Collection<TProperty> ids) {
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
    default <TProperty> ProxyQueryable2<T1Proxy,T1,T2Proxy,T2> whereByIds(boolean condition, Collection<TProperty> ids){

        if (condition) {
            getClientQueryable2().whereByIds(ids);
        }
        return getQueryable2();
    }

    /**
     * 使用对象进行查询 配合{@link com.easy.query.core.annotation.EasyWhereCondition} 设置条件对应的表和条件值
     * whereObject(request)
     * @param object 查询对象
     * @return
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配
     */
    default ProxyQueryable2<T1Proxy,T1,T2Proxy,T2> whereObject(Object object) {
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
   default ProxyQueryable2<T1Proxy,T1,T2Proxy,T2> whereObject(boolean condition, Object object){

       if (condition) {
           getClientQueryable2().whereObject(object);
       }
       return getQueryable2();
   }
}
