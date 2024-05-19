package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

/**
 * create time 2023/8/17 11:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityJoinable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {

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
     * @param <T2Proxy> join对象的代理
     * @param <T2> join对象
     */

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2,T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(Class<T2> joinClass, SQLExpression2<T1Proxy, T2Proxy> onExpression);
    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(T2Proxy t2Proxy, SQLExpression2<T1Proxy, T2Proxy> onExpression);

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
     * @param <T2Proxy> join对象的代理
     * @param <T2> join对象
     */
    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLExpression2<T1Proxy, T2Proxy> onExpression);

    /**
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
     * @param <T2Proxy> join对象的代理
     * @param <T2> join对象
     */
    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2,T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(Class<T2> joinClass, SQLExpression2<T1Proxy, T2Proxy> onExpression);
    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(T2Proxy t2Proxy, SQLExpression2<T1Proxy, T2Proxy> onExpression);

    /**
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
     * @param <T2Proxy> join对象的代理
     * @param <T2> join对象
     */
    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLExpression2<T1Proxy, T2Proxy> onExpression);

    /**
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
     * @param <T2Proxy> join对象的代理
     * @param <T2> join对象
     */
    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2,T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(Class<T2> joinClass, SQLExpression2<T1Proxy, T2Proxy> onExpression);
    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(T2Proxy t2Proxy, SQLExpression2<T1Proxy, T2Proxy> onExpression);

    /**
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
     * @param <T2Proxy> join对象的代理
     * @param <T2> join对象
     */
    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLExpression2<T1Proxy, T2Proxy> onExpression);



}
