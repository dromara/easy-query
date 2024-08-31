package com.easy.query.api.proxy.entity.select.extension.queryable2;

import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.core.common.tuple.MergeTuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityFilterable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientEntityQueryable2Available<T1, T2>, EntityQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    /**
     * 构建where条件
     *
     * <blockquote><pre>
     * {@code
     * where((a,b)->a.id().eq("123")) //id = '123'
     * where((a,b)->a.id().eq(o.name())) //id = name
     * where((a,b)->a.id().nullDefault("123").eq("123")) //ifnull(id,'123') = '123'
     * where((a,b)->{
     *     a.id().eq("123"); //a.id = '123' AND b.name like '%456%'
     *     b.name().like("456")
     * })
     *
     * where((a,b)->{   //(a.id = '123' OR b.name like '%456%')
     *     a.or(()->{
     *         a.id().eq("123");
     *         b.name().like("456")
     *     })
     * })
     *    }
     * </pre></blockquote>
     * @param whereExpression 入参两个参数分别为from表和join表
     * @return 返回表达式自身
     */
    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> where(SQLExpression2<T1Proxy, T2Proxy> whereExpression) {
        return where(true, whereExpression);
    }

    /**
     * 构建where条件
     *
     * <blockquote><pre>
     * {@code
     * where(true/false,(a,b)->a.id().eq("123")) //id = '123'
     * where(true/false,(a,b)->a.id().eq(o.name())) //id = name
     * where(true/false,(a,b)->a.id().nullDefault("123").eq("123")) //ifnull(id,'123') = '123'
     * where(true/false,(a,b)->{
     *     a.id().eq("123"); //a.id = '123' AND b.name like '%456%'
     *     b.name().like("456")
     * })
     *
     * where(true/false,(a,b)->{   //(a.id = '123' OR b.name like '%456%')
     *     a.or(()->{
     *         a.id().eq("123");
     *         b.name().like("456")
     *     })
     * })
     *    }
     * </pre></blockquote>
     * @param condition 是否需要动态添加当前where
     * @param whereExpression 入参两个参数分别为from表和join表
     * @return 返回表达式自身
     */
    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> where(boolean condition, SQLExpression2<T1Proxy, T2Proxy> whereExpression) {
        if (condition) {
            getClientQueryable2().where((wherePredicate1, wherePredicate2) -> {
                get1Proxy().getEntitySQLContext()._where(wherePredicate1.getFilter(),()->{
                    whereExpression.apply(get1Proxy(), get2Proxy());
                });
            });
        }
        return getQueryable2();
    }

    /**
     * 构建where条件
     *
     * <blockquote><pre>
     * {@code
     * whereMerge(o->o.t1.id().eq("123")) //id = '123'
     * whereMerge(o->o.t1.id().eq(o.name())) //id = name
     * whereMerge(o->o.t1.id().nullDefault("123").eq("123")) //ifnull(id,'123') = '123'
     * whereMerge(o->{
     *     o.t1.id().eq("123"); //a.id = '123' AND b.name like '%456%'
     *     o.t2.name().like("456")
     * })
     *
     * whereMerge(o->{   //(a.id = '123' OR b.name like '%456%')
     *     o.or(()->{
     *         o.t1.id().eq("123");
     *         o.t2.name().like("456")
     *     })
     * })
     *    }
     * </pre></blockquote>
     * @param whereExpression 入参为join的from表和join表组成的tuple元组
     * @return 返回表达式自身
     */
    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> whereMerge(SQLExpression1<MergeTuple2<T1Proxy, T2Proxy>> whereExpression) {
        return whereMerge(true, whereExpression);
    }
    /**
     * 构建where条件
     *
     * <blockquote><pre>
     * {@code
     * whereMerge(true/false,o->o.t1.id().eq("123")) //id = '123'
     * whereMerge(true/false,o->o.t1.id().eq(o.name())) //id = name
     * whereMerge(true/false,o->o.t1.id().nullDefault("123").eq("123")) //ifnull(id,'123') = '123'
     * whereMerge(true/false,o->{
     *     o.t1.id().eq("123"); //a.id = '123' AND b.name like '%456%'
     *     o.t2.name().like("456")
     * })
     *
     * whereMerge(true/false,o->{   //(a.id = '123' OR b.name like '%456%')
     *     o.or(()->{
     *         o.t1.id().eq("123");
     *         o.t2.name().like("456")
     *     })
     * })
     *    }
     * </pre></blockquote>
     * @param condition 是否需要动态添加当前where
     * @param whereExpression 入参为join的from表和join表组成的tuple元组
     * @return 返回表达式自身
     */
    default EntityQueryable2<T1Proxy, T1, T2Proxy, T2> whereMerge(boolean condition, SQLExpression1<MergeTuple2<T1Proxy, T2Proxy>> whereExpression) {
        return where(condition, (t1, t2) -> {
            whereExpression.apply(new MergeTuple2<>(t1, t2));
        });
    }
}
