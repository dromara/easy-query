package com.easy.query.api4j.select;

import com.easy.query.api4j.select.extension.queryable.ClientQueryableAvailable;
import com.easy.query.api4j.select.extension.queryable.SQLAggregate1Extension;
import com.easy.query.api4j.select.extension.queryable.SQLFill1Extension;
import com.easy.query.api4j.select.extension.queryable.SQLGroup1Extension;
import com.easy.query.api4j.select.extension.queryable.SQLHaving1Extension;
import com.easy.query.api4j.select.extension.queryable.SQLInclude1Extension;
import com.easy.query.api4j.select.extension.queryable.SQLJoin1Extension;
import com.easy.query.api4j.select.extension.queryable.SQLOrderBy1Extension;
import com.easy.query.api4j.select.extension.queryable.SQLSelect1Extension;
import com.easy.query.api4j.select.extension.queryable.SQLUnion1Extension;
import com.easy.query.api4j.select.extension.queryable.SQLWhere1Extension;
import com.easy.query.api4j.sql.SQLColumnSelector;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.QueryStrategy;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;

/**
 * @author xuejiaming
 * @FileName: Select0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 21:28
 */
public interface Queryable<T1> extends Query<T1>,
        Interceptable<Queryable<T1>>,
        LogicDeletable<Queryable<T1>>,
        TableReNameable<Queryable<T1>>,
        QueryStrategy<Queryable<T1>>,
        SQLAggregate1Extension<T1>,
        SQLGroup1Extension<T1>,
        SQLHaving1Extension<T1>,
        SQLJoin1Extension<T1>,
        SQLOrderBy1Extension<T1>,
        SQLSelect1Extension<T1>,
        SQLUnion1Extension<T1>,
        SQLWhere1Extension<T1>,
        SQLInclude1Extension<T1>,
        SQLFill1Extension<T1>,
        ClientQueryableAvailable<T1> {

    /**
     * 只clone表达式共享上下文
     * 如果是两个独立的表达式建议重新创建如果是
     *
     * @return
     */
    @Override
    Queryable<T1> cloneQueryable();

    long countDistinct(SQLExpression1<SQLColumnSelector<T1>> selectExpression);


    /**
     * SELECT NOT EXISTS (
     * SELECT 1
     * FROM `table` AS `t`
     * WHERE (`t`.`columns` = ?))
     *
     * @param whereExpression 表达式最后一个是取反
     * @return
     */
    boolean all(SQLExpression1<SQLWherePredicate<T1>> whereExpression);


    /**
     * 设置column所有join表都会生效
     *
     * @param columns
     * @return
     */
    @Override
    Queryable<T1> select(String columns);

    default Queryable<T1> distinct() {
        return distinct(true);
    }

    Queryable<T1> distinct(boolean condition);

    @Override
    default Queryable<T1> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default Queryable<T1> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default Queryable<T1> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    Queryable<T1> limit(boolean condition, long offset, long rows);


    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     * 如果当前启用了追踪查询并且在当前上下文已经追加了当前trackKey的对象那么当前查询结果的对象不会被返回,返回的是被追踪的当前对象,
     * 如果对象A:{id:1,name:2}已经被追踪了,新查询的结果是对象A:{id:1,name:3},那么查询到的数据是{id:1,name:3}但是用户获取到的数据是{id:1,name:2}
     * 所以尽可能在追踪后调用entity update,而不是重复查询对应对象
     *
     * @return
     */
    Queryable<T1> asTracking();

    Queryable<T1> asNoTracking();

    Queryable<T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    Queryable<T1> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    Queryable<T1> useConnectionMode(ConnectionModeEnum connectionMode);

//    SQLExpressionProvider<T1> getSQLExpressionProvider1();

}
