package com.easy.query.api.proxy.sql.core.filter;

import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.filter.FilterAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/9/25 17:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyAssertPredicate<TChain> extends FilterAvailable, ChainCast<TChain> {


    /**
     * column is null
     */
    default <TProxy extends ProxyEntity<TProxy,T>,T,TProperty> TChain isNull(SQLColumn<TProxy,TProperty> column) {
        return isNull(true, column);
    }

    /**
     * column is null
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    default <TProxy extends ProxyEntity<TProxy,T>,T,TProperty> TChain isNull(boolean condition, SQLColumn<TProxy,TProperty> column) {
        if (condition) {
            getFilter().isNull(column.getTable(), column.value());
        }
        return castChain();
    }

    /**
     * column is not null
     */
    default <TProxy extends ProxyEntity<TProxy,T>,T,TProperty> TChain isNotNull(SQLColumn<TProxy,TProperty> column) {
        return isNotNull(true, column);
    }

    /**
     * column is not null
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    default <TProxy extends ProxyEntity<TProxy,T>,T,TProperty> TChain isNotNull(boolean condition, SQLColumn<TProxy,TProperty> column) {
        if (condition) {
            getFilter().isNotNull(column.getTable(), column.value());
        }
        return castChain();
    }
}
