package com.easy.query.core.basic.api.insert;

import com.easy.query.core.basic.api.internal.SqlExecuteRows;
import com.easy.query.core.basic.api.select.Queryable;

import java.util.Collection;
import java.util.function.Function;

/**
 * @FileName: Insertable.java
 * @Description: 文件说明
 * @Date: 2023/2/20 08:48
 * @author xuejiaming
 */
public interface Insertable<T> extends SqlExecuteRows {
    Insertable<T> insert(T entity);

    default Insertable<T> insert(Collection<T> entities) {
        for (T entity : entities) {
            insert(entity);
        }
        return this;
    }

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     * @param tableName
     * @return
     */
    default Insertable<T> asTable(String tableName){
        return asTable(old->tableName);
    }
    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableNameAs}返回的表名
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     * @param tableNameAs
     * @return
     */
    Insertable<T> asTable(Function<String,String> tableNameAs);

    /**
     *
     * @param fillAutoIncrement
     * @return
     */
    long executeRows(boolean fillAutoIncrement);

    @Override
    default long executeRows(){
        return executeRows(false);
    }

    String toSql();
}
