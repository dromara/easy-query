package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

import java.math.BigDecimal;

/**
 * create time 2023/12/18 09:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityCountable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {


    /**
     * SELECT COUNT(*) FROM TABLE
     */
    default Query<Long> selectCount() {
        return selectCount(Long.class);
    }


    /**
     * SELECT COUNT(*) FROM TABLE
     *
     * @param numberClass 自定义返回结果字节
     * @param <TNumber>   返回结果类型
     * @return 返回当前queryable链式
     */
    <TNumber extends Number> Query<TNumber> selectCount(Class<TNumber> numberClass);

    /**
     * 建议使用.selectColumn(x->x.age().sum())
     * @param columnSelector
     * @return
     * @param <TMember>
     */
    @Deprecated
    <TMember extends Number> Query<TMember> selectSum(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector);

    /**
     *
     * 建议使用.selectColumn(x->x.age().avg())
     * @param columnSelector
     * @return
     * @param <TMember>
     */
    @Deprecated
    <TMember extends Number> Query<BigDecimal> selectAvg(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector);

    /**
     *
     * 建议使用.selectColumn(x->x.age().max())
     * @param columnSelector
     * @return
     * @param <TMember>
     */
    @Deprecated
    <TMember> Query<TMember> selectMax(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector);

    /**
     *
     * 建议使用.selectColumn(x->x.age().min())
     * @param columnSelector
     * @return
     * @param <TMember>
     */
    @Deprecated
    <TMember> Query<TMember> selectMin(SQLFuncExpression1<T1Proxy, SQLColumn<T1Proxy, TMember>> columnSelector);


}
