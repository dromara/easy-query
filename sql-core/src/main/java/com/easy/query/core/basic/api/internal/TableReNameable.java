package com.easy.query.core.basic.api.internal;

import com.easy.query.core.util.EasyStringUtil;

import java.util.function.Function;

/**
 * create time 2023/4/2 22:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableReNameable<TChain> {
    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     * @throws IllegalArgumentException tableName为null时抛错
     * @param tableName
     * @return
     */
    default TChain asTable(String tableName){
        if(EasyStringUtil.isBlank(tableName)){
            throw new IllegalArgumentException("tableName is empty");
        }
        return asTable(old->tableName);
    }
    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableNameAs}返回的表名
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表则不更改
     * @param tableNameAs
     * @return
     */
    TChain asTable(Function<String,String> tableNameAs);

    /**
     * 将当前表达式最近的一张表的别名进行指定
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param alias 别名
     * @return
     */
    TChain asAlias(String alias);
}
