package com.easy.query.core.basic.api.delete;

import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.SqlExecuteExpectRows;
import com.easy.query.core.basic.api.select.Queryable;

import java.util.function.Function;

/**
 * @FileName: Deletable.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:19
 * @author xuejiaming
 */
public interface Deletable<T, TChain> extends SqlExecuteExpectRows, LogicDeletable<TChain> {
    /**
     * 语句转成sql
     * @return
     */
    String toSql();

    /**
     * 是否允许删除命令
     *
     * @param allow
     * @return
     */
    TChain allowDeleteCommand(boolean allow);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     * @param tableName
     * @return
     */
    default TChain asTable(String tableName){
        return asTable(old->tableName);
    }
    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableNameAs}返回的表名
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     * @param tableNameAs
     * @return
     */
    TChain asTable(Function<String,String> tableNameAs);
}
