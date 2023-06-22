package com.easy.query.api.proxy.select;

import com.easy.query.api.proxy.core.ProxyQuery;
import com.easy.query.api.proxy.core.base.SQLColumn;
import com.easy.query.api.proxy.sql.SQLAsSelector;
import com.easy.query.api.proxy.sql.SQLFilter;
import com.easy.query.api.proxy.sql.SQLSelector;
import com.easy.query.api.proxy.sql.impl.SQLFilterImpl;
import com.easy.query.api.proxy.sql.impl.SQLSelectorImpl;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.QueryStrategy;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.exception.EasyQueryMultiPrimaryKeyException;
import com.easy.query.core.exception.EasyQueryNoPrimaryKeyException;
import com.easy.query.core.exception.EasyQueryWhereInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.segment.ColumnSegment;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

/**
 * create time 2023/6/21 17:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryable<T1Proxy extends ProxyQuery<T1Proxy, T1>, T1> extends Query<T1>,
        Interceptable<ProxyQueryable<T1Proxy, T1>>,
        LogicDeletable<ProxyQueryable<T1Proxy, T1>>,
        TableReNameable<ProxyQueryable<T1Proxy, T1>>,
        QueryStrategy<ProxyQueryable<T1Proxy, T1>> {
    T1Proxy get1Proxy();

    ClientProxyQueryable<T1Proxy, T1> getEntityQueryable();

    @Override
    ProxyQueryable<T1Proxy, T1> cloneQueryable();

   default long countDistinct(SQLExpression2<T1Proxy, SQLSelector> selectExpression){
       return getEntityQueryable().countDistinct(selector->{
           selectExpression.apply(get1Proxy(),new SQLSelectorImpl(selector.getSelector()));
       });
   }

    /**
     * SELECT NOT EXISTS (
     * SELECT 1
     * FROM `table` AS `t`
     * WHERE (`t`.`columns` = ?))
     *
     * @param whereExpression 表达式最后一个是取反
     * @return
     */
    default boolean all(SQLExpression2<T1Proxy, SQLFilter> whereExpression) {
        return getEntityQueryable().all(where -> {
                    whereExpression.apply(get1Proxy(), new SQLFilterImpl(where.getFilter()));
                });
    }

    /**
     * 防止溢出
     *
     * @param columnSelector
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(Function<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return sumBigDecimalOrDefault(columnSelector, null);
    }

    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(Function<T1Proxy, SQLColumn<TMember>> columnSelector, BigDecimal def) {
        return getEntityQueryable().sumBigDecimalOrDefault(columnSelector.apply(get1Proxy()).value(),def);
    }

    default <TMember extends Number> TMember sumOrNull(Function<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getEntityQueryable().sumOrNull(columnSelector.apply(get1Proxy()).value());
    }

    default <TMember extends Number> TMember sumOrDefault(Function<T1Proxy, SQLColumn<TMember>> columnSelector, TMember def) {
        return getEntityQueryable().sumOrDefault(columnSelector.apply(get1Proxy()).value(), def);
    }

    default <TMember extends Comparable<?>> TMember maxOrNull(Function<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getEntityQueryable().maxOrNull(columnSelector.apply(get1Proxy()).value());
    }

    default <TMember extends Comparable<?>> TMember maxOrDefault(Function<T1Proxy, SQLColumn<TMember>> columnSelector, TMember def) {
        return getEntityQueryable().maxOrDefault(columnSelector.apply(get1Proxy()).value(), def);
    }

    default <TMember> TMember minOrNull(Function<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getEntityQueryable().minOrNull(columnSelector.apply(get1Proxy()).value());
    }

    default <TMember> TMember minOrDefault(Function<T1Proxy, SQLColumn<TMember>> columnSelector, TMember def) {
        return getEntityQueryable().minOrDefault(columnSelector.apply(get1Proxy()).value(), def);
    }

    default <TMember extends Number> Double avgOrNull(Function<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getEntityQueryable().avgOrNull(columnSelector.apply(get1Proxy()).value());
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(Function<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getEntityQueryable().avgBigDecimalOrNull(columnSelector.apply(get1Proxy()).value());
    }

    default <TMember extends Number> Float avgFloatOrNull(Function<T1Proxy, SQLColumn<TMember>> columnSelector) {
        return getEntityQueryable().avgFloatOrNull(columnSelector.apply(get1Proxy()).value());
    }

    default <TMember extends Number> Double avgOrDefault(Function<T1Proxy, SQLColumn<TMember>> columnSelector, Double def) {
        return getEntityQueryable().avgOrDefault(columnSelector.apply(get1Proxy()).value(), def);
    }

    default BigDecimal avgOrDefault(Function<T1Proxy, SQLColumn<BigDecimal>> columnSelector, BigDecimal def) {
        return getEntityQueryable().avgOrDefault(columnSelector.apply(get1Proxy()).value(), def);
    }

    default Float avgOrDefault(Function<T1Proxy, SQLColumn<Float>> columnSelector, Float def) {
        return getEntityQueryable().avgOrDefault(columnSelector.apply(get1Proxy()).value(), def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(Function<T1Proxy, SQLColumn<TMember>> columnSelector, TResult def, Class<TResult> resultClass) {
        return getEntityQueryable().avgOrDefault(columnSelector.apply(get1Proxy()).value(), def, resultClass);
    }


    /**
     * 对当前表达式返回自定义select列
     *
     * @param selectExpression
     * @return
     */
   ProxyQueryable<T1Proxy, T1> select(SQLExpression2<T1Proxy, SQLSelector> selectExpression);

    /**
     * 将当前T1对象转成TR对象，select会将T1属性所对应的列名映射到TR对象的列名上(忽略大小写)
     * T1.property1列名叫做column1,T1.property2列名叫做column2，TR.property3的列名也叫column1
     * 那么生成的sql为:select column1 from t1
     * 如果当前存在join，那么join的子表一律不会映射到resultClass上,如果需要那么请手动调用双参数select
     *
     * @param trProxy
     * @param <TRProxy>
     * @param <TR>
     * @return
     */
    <TRProxy extends ProxyQuery<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(ProxyQuery<TRProxy, TR> trProxy);

    /**
     * 设置返回对象，返回对象会根据selectExpression映射相同列名
     * 多次select会在前一次基础上进行对上次结果进行匿名表处理
     *
     * @param trProxy
     * @param selectExpression
     * @param <TR>
     * @return
     */
    <TRProxy extends ProxyQuery<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression3<T1Proxy, TRProxy, SQLAsSelector> selectExpression);

    /**
     * 设置column所有join表都会生效
     *
     * @param columns
     * @return
     */
    @Override
    ProxyQueryable<T1Proxy, T1>  select(String columns);
    default ProxyQueryable<T1Proxy, T1> select(ColumnSegment columnSegment, boolean clearAll){
        return select(Collections.singletonList(columnSegment), clearAll);
    }

    ProxyQueryable<T1Proxy, T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll);
    default ProxyQueryable<T1Proxy, T1> where(SQLExpression2<T1Proxy, SQLFilter> whereExpression) {
        return where(true, whereExpression);
    }

    ProxyQueryable<T1Proxy, T1> where(boolean condition, SQLExpression2<T1Proxy, SQLFilter> whereExpression);

    /**
     * 根据id主键查询
     *
     * @param id 主键
     * @return 链式表达式
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException @description 无主键或者多主键报错
     */

    default ProxyQueryable<T1Proxy, T1> whereById(Object id) {
        return whereById(true, id);
    }

    /**
     * @param condition
     * @param id
     * @return
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException @description 无主键或者多主键报错
     */

    ProxyQueryable<T1Proxy, T1> whereById(boolean condition, Object id);

    /**
     * @param ids
     * @param <TProperty>
     * @return
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException
     */

    default <TProperty> ProxyQueryable<T1Proxy, T1> whereByIds(Collection<TProperty> ids) {
        return whereByIds(true, ids);
    }

    /**
     * 根据主键id集合查询
     *
     * @param condition   是否添加该条件
     * @param ids         主键集合
     * @param <TProperty> 主键类型
     * @return 当前链式表达式
     * @throws EasyQueryNoPrimaryKeyException,EasyQueryMultiPrimaryKeyException
     */
    <TProperty> ProxyQueryable<T1Proxy, T1> whereByIds(boolean condition, Collection<TProperty> ids);

    /**
     * @param object
     * @return
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配
     */
    default ProxyQueryable<T1Proxy, T1> whereObject(Object object) {
        return whereObject(true, object);
    }

    /**
     * @param condition
     * @param object
     * @return
     * @throws EasyQueryWhereInvalidOperationException 当object的where属性和查询对象不匹配或者查询对象属性不匹配
     */
    ProxyQueryable<T1Proxy, T1> whereObject(boolean condition, Object object);

//    SQLQueryable<TProxy, TEntity> select(SQLExpression1<TSelect> selectExpression);
//
//    <TProxy1 extends SQLQuery<TProxy1,TEntity1>,TEntity1> SQLQueryable<TProxy1,TEntity1> select(TProxy1 selector, SQLExpression2<TSelect,TSelect1> selectExpression);
}
