package com.easy.query.core.proxy.sql.scec;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

import java.text.MessageFormat;
import java.util.Collection;

/**
 * create time 2023/9/28 21:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNativeProxyExpressionChain<TChain> {
    <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity, TProperty> TChain expression(SQLColumn<TEntityProxy, TProperty> sqlColumn);

    <TEntity> TChain expression(Query<TEntity> subQuery);

    <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity, TProperty> TChain columnName(SQLColumn<TEntityProxy, TProperty> sqlColumn,String columnName);


    TChain value(Object val);
    /**
     * 将参数以[?,?,?]参数化形式拼接到sql中
     * @param values
     * @return
     * @param <T>
     */
    <T> TChain collection(Collection<T> values);

    TChain format(Object formatVal);

    TChain setAlias(String alias);
    default <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity, TProperty> TChain setAlias(SQLColumn<TEntityProxy, TProperty> sqlColumn){
        return setAlias(sqlColumn.getValue());
    }

    /**
     * 使用message format 格式化风格 单引号作为转义
     * @return
     */
    TChain messageFormat();
}
