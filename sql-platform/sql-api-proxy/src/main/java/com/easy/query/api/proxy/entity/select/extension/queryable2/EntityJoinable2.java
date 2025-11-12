package com.easy.query.api.proxy.entity.select.extension.queryable2;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable3;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.common.tuple.MergeTuple3;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression3;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityJoinable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientEntityQueryable2Available<T1, T2>, EntityQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    /**
     * 创建join可查询对象
     * 单个条件比如Province表的id和City表的provinceId
     * 生成的sql from province p left join city c on p.id=c.province_id
     * <blockquote><pre>
     * {@code
     * queryable(Province.class)
     * .leftJoin(City.class, (p, c) -> p.id().eq(c.provinceId()))
     * }
     * </pre></blockquote>
     *
     * 多条件比如Province表的id和City表的provinceId外加City表的状态
     * 生成的sql from province p left join city c on p.id=c.province_id and c.status=1
     * <blockquote><pre>
     * {@code
     *
     * queryable(Province.class)
     * .leftJoin(City.class, (p, c) -> {
     *     p.id().eq(c.provinceId());
     *     c.status().eq(1);
     * })
     * }
     * </pre></></blockquote>
     *
     * 多个join下参数会变成多个
     * 生成的sql from province p left join city c on p.id=c.province_id left join area a on p.id=a.province_id
     * <blockquote><pre>
     * {@code
     * queryable(Province.class)
     * .leftJoin(City.class, (p, c) -> p.id().eq(c.provinceId()))
     * .leftJoin(Area.class, (p, c, a) -> p.id().eq(a.provinceId()))
     * }
     * </pre></blockquote>
     * @param joinClass join的对象字节
     * @param onExpression join的条件,入参个数取决于join的表数目,一次join入参两个后续依次递增
     * @return 返回可查询的对象
     * @param <T3Proxy> join对象的代理
     * @param <T3> join对象
     */
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> leftJoin(Class<T3> joinClass, SQLActionExpression3<T1Proxy, T2Proxy, T3Proxy> onExpression) {
        T3Proxy t3Proxy = EntityQueryProxyManager.create(joinClass);
        return leftJoin(t3Proxy,onExpression);
    }
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> leftJoin(T3Proxy t3Proxy, SQLActionExpression3<T1Proxy, T2Proxy, T3Proxy> onExpression) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().leftJoin(t3Proxy.getEntityClass(), (t, t1, t2) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                onExpression.apply(get1Proxy(), get2Proxy(), t3Proxy.create(t2.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable3<>(get1Proxy(), get2Proxy(), t3Proxy, entityQueryable3);
    }

    /**
     * 创建join可查询对象
     * 单个条件比如Province表的id和City表的provinceId
     * 生成的sql from province p left join (select * from city where status=1) c on p.id=c.province_id
     * <blockquote><pre>
     * {@code
     * var cityQuery=queryable(City.class).where(o->o.status().eq(1))
     * queryable(Province.class)
     * .leftJoin(cityQuery, (p, c) -> p.id().eq(c.provinceId()))
     * }
     * </pre></blockquote>
     * @param joinQueryable join的对象表达式
     * @param onExpression join的条件,入参个数取决于join的表数目,一次join入参两个后续依次递增
     * @return 返回可查询的对象
     * @param <T3Proxy> join对象的代理
     * @param <T3> join对象
     */
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> leftJoin(EntityQueryable<T3Proxy, T3> joinQueryable, SQLActionExpression3<T1Proxy, T2Proxy, T3Proxy> onExpression) {
        if(EasySQLExpressionUtil.useTableForJoin(joinQueryable.getSQLEntityExpressionBuilder())){
            return leftJoin(joinQueryable.get1Proxy(),onExpression);
        }
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                onExpression.apply(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy().create(t2.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable3<>(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy(), entityQueryable3);

    }

    /**
     *
     * 创建join可查询对象
     * 单个条件比如Province表的id和City表的provinceId
     * 生成的sql from province p right join city c on p.id=c.province_id
     * <blockquote><pre>
     * {@code
     * queryable(Province.class)
     * .rightJoin(City.class, (p, c) -> p.id().eq(c.provinceId()))
     * }
     * </pre></blockquote>
     *
     * 多条件比如Province表的id和City表的provinceId外加City表的状态
     * 生成的sql from province p right join city c on p.id=c.province_id and c.status=1
     * <blockquote><pre>
     * {@code
     *
     * queryable(Province.class)
     * .rightJoin(City.class, (p, c) -> {
     *     p.id().eq(c.provinceId());
     *     c.status().eq(1);
     * })
     * }
     * </pre></></blockquote>
     *
     * 多个join下参数会变成多个
     * 生成的sql from province p right join city c on p.id=c.province_id right join area a on p.id=a.province_id
     * <blockquote><pre>
     * {@code
     * queryable(Province.class)
     * .rightJoin(City.class, (p, c) -> p.id().eq(c.provinceId()))
     * .rightJoin(Area.class, (p, c, a) -> p.id().eq(a.provinceId()))
     * }
     * </pre></blockquote>
     * @param joinClass join的对象字节
     * @param onExpression join的条件,入参个数取决于join的表数目,一次join入参两个后续依次递增
     * @return 返回可查询的对象
     * @param <T3Proxy> join对象的代理
     * @param <T3> join对象
     */
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> rightJoin(Class<T3> joinClass, SQLActionExpression3<T1Proxy, T2Proxy, T3Proxy> onExpression) {
        T3Proxy t3Proxy = EntityQueryProxyManager.create(joinClass);
        return rightJoin(t3Proxy,onExpression);

    }
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> rightJoin(T3Proxy t3Proxy, SQLActionExpression3<T1Proxy, T2Proxy, T3Proxy> onExpression) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().rightJoin(t3Proxy.getEntityClass(), (t, t1, t2) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                onExpression.apply(get1Proxy(), get2Proxy(), t3Proxy.create(t2.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable3<>(get1Proxy(), get2Proxy(), t3Proxy, entityQueryable3);

    }

    /**
     *
     * 创建join可查询对象
     * 单个条件比如Province表的id和City表的provinceId
     * 生成的sql from province p right join (select * from city where status=1) c on p.id=c.province_id
     * <blockquote><pre>
     * {@code
     * var cityQuery=queryable(City.class).where(o->o.status().eq(1))
     * queryable(Province.class)
     * .rightJoin(cityQuery, (p, c) -> p.id().eq(c.provinceId()))
     * }
     * </pre></blockquote>
     * @param joinQueryable join的对象表达式
     * @param onExpression join的条件,入参个数取决于join的表数目,一次join入参两个后续依次递增
     * @return 返回可查询的对象
     * @param <T3Proxy> join对象的代理
     * @param <T3> join对象
     */
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> rightJoin(EntityQueryable<T3Proxy, T3> joinQueryable, SQLActionExpression3<T1Proxy, T2Proxy, T3Proxy> onExpression) {
        if(EasySQLExpressionUtil.useTableForJoin(joinQueryable.getSQLEntityExpressionBuilder())){
            return rightJoin(joinQueryable.get1Proxy(),onExpression);
        }
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                onExpression.apply(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy().create(t2.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable3<>(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy(), entityQueryable3);

    }

    /**
     *
     * 创建join可查询对象
     * 单个条件比如Province表的id和City表的provinceId
     * 生成的sql from province p inner join city c on p.id=c.province_id
     * <blockquote><pre>
     * {@code
     * queryable(Province.class)
     * .innerJoin(City.class, (p, c) -> p.id().eq(c.provinceId()))
     * }
     * </pre></blockquote>
     *
     * 多条件比如Province表的id和City表的provinceId外加City表的状态
     * 生成的sql from province p inner join city c on p.id=c.province_id and c.status=1
     * <blockquote><pre>
     * {@code
     *
     * queryable(Province.class)
     * .innerJoin(City.class, (p, c) -> {
     *     p.id().eq(c.provinceId());
     *     c.status().eq(1);
     * })
     * }
     * </pre></></blockquote>
     *
     * 多个join下参数会变成多个
     * 生成的sql from province p inner join city c on p.id=c.province_id inner join area a on p.id=a.province_id
     * <blockquote><pre>
     * {@code
     * queryable(Province.class)
     * .innerJoin(City.class, (p, c) -> p.id().eq(c.provinceId()))
     * .innerJoin(Area.class, (p, c, a) -> p.id().eq(a.provinceId()))
     * }
     * </pre></blockquote>
     * @param joinClass join的对象字节
     * @param onExpression join的条件,入参个数取决于join的表数目,一次join入参两个后续依次递增
     * @return 返回可查询的对象
     * @param <T3Proxy> join对象的代理
     * @param <T3> join对象
     */
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> innerJoin(Class<T3> joinClass, SQLActionExpression3<T1Proxy, T2Proxy, T3Proxy> onExpression) {
        T3Proxy t3Proxy = EntityQueryProxyManager.create(joinClass);
        return innerJoin(t3Proxy,onExpression);

    }
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> innerJoin(T3Proxy t3Proxy, SQLActionExpression3<T1Proxy, T2Proxy, T3Proxy> onExpression) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().innerJoin(t3Proxy.getEntityClass(), (t, t1, t2) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                onExpression.apply(get1Proxy(), get2Proxy(), t3Proxy.create(t2.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable3<>(get1Proxy(), get2Proxy(), t3Proxy, entityQueryable3);

    }

    /**
     *
     * 创建join可查询对象
     * 单个条件比如Province表的id和City表的provinceId
     * 生成的sql from province p inner join (select * from city where status=1) c on p.id=c.province_id
     * <blockquote><pre>
     * {@code
     * var cityQuery=queryable(City.class).where(o->o.status().eq(1))
     * queryable(Province.class)
     * .innerJoin(cityQuery, (p, c) -> p.id().eq(c.provinceId()))
     * }
     * </pre></blockquote>
     * @param joinQueryable join的对象表达式
     * @param onExpression join的条件,入参个数取决于join的表数目,一次join入参两个后续依次递增
     * @return 返回可查询的对象
     * @param <T3Proxy> join对象的代理
     * @param <T3> join对象
     */
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> innerJoin(EntityQueryable<T3Proxy, T3> joinQueryable, SQLActionExpression3<T1Proxy, T2Proxy, T3Proxy> onExpression) {
        if(EasySQLExpressionUtil.useTableForJoin(joinQueryable.getSQLEntityExpressionBuilder())){
            return innerJoin(joinQueryable.get1Proxy(),onExpression);
        }
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                onExpression.apply(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy().create(t2.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable3<>(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy(), entityQueryable3);

    }
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> crossJoin(Class<T3> joinClass, SQLActionExpression3<T1Proxy, T2Proxy, T3Proxy> onExpression) {
        T3Proxy t3Proxy = EntityQueryProxyManager.create(joinClass);
        return crossJoin(t3Proxy,onExpression);

    }
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> crossJoin(T3Proxy t3Proxy, SQLActionExpression3<T1Proxy, T2Proxy, T3Proxy> onExpression) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().crossJoin(t3Proxy.getEntityClass(), (t, t1, t2) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                onExpression.apply(get1Proxy(), get2Proxy(), t3Proxy.create(t2.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable3<>(get1Proxy(), get2Proxy(), t3Proxy, entityQueryable3);

    }
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> crossJoin(EntityQueryable<T3Proxy, T3> joinQueryable, SQLActionExpression3<T1Proxy, T2Proxy, T3Proxy> onExpression) {
        if(EasySQLExpressionUtil.useTableForJoin(joinQueryable.getSQLEntityExpressionBuilder())){
            return crossJoin(joinQueryable.get1Proxy(),onExpression);
        }
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().crossJoin(joinQueryable.getClientQueryable(), (t, t1, t2) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                onExpression.apply(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy().create(t2.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable3<>(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy(), entityQueryable3);
    }


    /**
     * 同join但是入参参数仅一个合并为tuple如leftJoin(XXX.class,o->o.t1.id().eq(o.t2.id()))
     * 当join表过多时又不需要使用其他表时可以采用这种模式
     * @param joinClass
     * @param onExpression
     * @return
     * @param <T3Proxy>
     * @param <T3>
     */
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> leftJoinMerge(Class<T3> joinClass, SQLActionExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>> onExpression) {
        return leftJoin(joinClass, (t1, t2, t3) -> {
            onExpression.apply(new MergeTuple3<>(t1, t2, t3));
        });
    }

    /**
     *
     * 同join但是入参参数仅一个合并为tuple如leftJoin(query(),o->o.t1.id().eq(o.t2.id()))
     * 当join表过多时又不需要使用其他表时可以采用这种模式
     * @param joinQueryable
     * @param onExpression
     * @return
     * @param <T3Proxy>
     * @param <T3>
     */
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> leftJoinMerge(EntityQueryable<T3Proxy, T3> joinQueryable, SQLActionExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>> onExpression) {
        return leftJoin(joinQueryable, (t1, t2, t3) -> {
            onExpression.apply(new MergeTuple3<>(t1, t2, t3));
        });
    }

    /**
     *
     * 同join但是入参参数仅一个合并为tuple如rightJoin(XXX.class,o->o.t1.id().eq(o.t2.id()))
     * 当join表过多时又不需要使用其他表时可以采用这种模式
     * @param joinClass
     * @param onExpression
     * @return
     * @param <T3Proxy>
     * @param <T3>
     */
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> rightJoinMerge(Class<T3> joinClass, SQLActionExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>> onExpression) {
        return rightJoin(joinClass, (t1, t2, t3) -> {
            onExpression.apply(new MergeTuple3<>(t1, t2, t3));
        });
    }

    /**
     *
     * 同join但是入参参数仅一个合并为tuple如rightJoin(query(),o->o.t1.id().eq(o.t2.id()))
     * 当join表过多时又不需要使用其他表时可以采用这种模式
     * @param joinQueryable
     * @param onExpression
     * @return
     * @param <T3Proxy>
     * @param <T3>
     */
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> rightJoinMerge(EntityQueryable<T3Proxy, T3> joinQueryable, SQLActionExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>> onExpression) {
        return rightJoin(joinQueryable, (t1, t2, t3) -> {
            onExpression.apply(new MergeTuple3<>(t1, t2, t3));
        });
    }

    /**
     *
     * 同join但是入参参数仅一个合并为tuple如innerJoin(XXX.class,o->o.t1.id().eq(o.t2.id()))
     * 当join表过多时又不需要使用其他表时可以采用这种模式
     * @param joinClass
     * @param onExpression
     * @return
     * @param <T3Proxy>
     * @param <T3>
     */
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> innerJoinMerge(Class<T3> joinClass, SQLActionExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>> onExpression) {
        return innerJoin(joinClass, (t1, t2, t3) -> {
            onExpression.apply( new MergeTuple3<>(t1, t2, t3));
        });
    }

    /**
     *
     * 同join但是入参参数仅一个合并为tuple如innerJoin(query(),o->o.t1.id().eq(o.t2.id()))
     * 当join表过多时又不需要使用其他表时可以采用这种模式
     * @param joinQueryable
     * @param onExpression
     * @return
     * @param <T3Proxy>
     * @param <T3>
     */
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> innerJoinMerge(EntityQueryable<T3Proxy, T3> joinQueryable, SQLActionExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>> onExpression) {
        return innerJoin(joinQueryable, (t1, t2, t3) -> {
            onExpression.apply(new MergeTuple3<>(t1, t2, t3));
        });
    }
    /**
     *
     * 同join但是入参参数仅一个合并为tuple如crossJoin(XXX.class,o->o.t1.id().eq(o.t2.id()))
     * 当join表过多时又不需要使用其他表时可以采用这种模式
     * @param joinClass
     * @param onExpression
     * @return
     * @param <T3Proxy>
     * @param <T3>
     */
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> crossJoinMerge(Class<T3> joinClass, SQLActionExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>> onExpression) {
        return crossJoin(joinClass, (t1, t2, t3) -> {
            onExpression.apply( new MergeTuple3<>(t1, t2, t3));
        });
    }

    /**
     *
     * 同join但是入参参数仅一个合并为tuple如crossJoin(query(),o->o.t1.id().eq(o.t2.id()))
     * 当join表过多时又不需要使用其他表时可以采用这种模式
     * @param joinQueryable
     * @param onExpression
     * @return
     * @param <T3Proxy>
     * @param <T3>
     */
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> crossJoinMerge(EntityQueryable<T3Proxy, T3> joinQueryable, SQLActionExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>> onExpression) {
        return crossJoin(joinQueryable, (t1, t2, t3) -> {
            onExpression.apply(new MergeTuple3<>(t1, t2, t3));
        });
    }

}
