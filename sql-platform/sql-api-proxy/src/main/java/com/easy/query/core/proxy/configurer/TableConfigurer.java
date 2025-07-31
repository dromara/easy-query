package com.easy.query.core.proxy.configurer;

import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyStringUtil;

import java.util.function.Function;

/**
 * create time 2025/7/30 13:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableConfigurer<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> {

    default TableConfigurer<TProxy, TEntity> disableLogicDelete() {
        return this.useLogicDelete(false);
    }
    default TableConfigurer<TProxy, TEntity> enableLogicDelete() {
        return this.useLogicDelete(true);
    }

    TableConfigurer<TProxy, TEntity> useLogicDelete(boolean use);


    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName 新的表名
     * @return
     * @throws IllegalArgumentException tableName为null时抛错
     */
    default TableConfigurer<TProxy, TEntity>  asTable(String tableName) {
        if (EasyStringUtil.isBlank(tableName)) {
            throw new IllegalArgumentException("tableName is empty");
        }
        return asTable(old -> tableName);
    }

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableNameAs}返回的表名
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表则不更改
     * asTable(old->old+xxx)
     *
     * @param tableNameAs 通过旧的表名生成新的表名
     * @return
     */
    TableConfigurer<TProxy, TEntity>  asTable(Function<String, String> tableNameAs);
    TableConfigurer<TProxy, TEntity>  asAlias(String alias);
}
