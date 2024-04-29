package com.easy.query.api.proxy.entity.select.extension;

import com.easy.query.core.basic.api.select.QueryAvailable;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.List;

/**
 * create time 2024/4/29 14:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface FlatListResultAble<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends QueryAvailable<T1> {


    /**
     * 返回所有的查询结果集
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param fetchResultExpression 映射对象
     * @param <TR>        映射对象类型
     * @return 获取查询结果集
     */
    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> List<TR> toList(SQLFuncExpression1<T1Proxy,TRProxy> fetchResultExpression);
}
