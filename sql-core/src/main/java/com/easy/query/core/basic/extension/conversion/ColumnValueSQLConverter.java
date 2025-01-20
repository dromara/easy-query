package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleSQLTableOwner;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/8/5 23:08
 * 对象列和数据库列用数据库函数转换
 *
 * @author xuejiaming
 */
public interface ColumnValueSQLConverter {
    boolean isRealColumn();


    /**
     * select查询
     *
     * @param table
     * @param columnMetadata
     * @param sqlPropertyConverter
     */
    void selectColumnConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext);

    /**
     *
     * insert update entity
     * update set
     * where
     *
     * @param table
     * @param columnMetadata
     * @param sqlPropertyConverter
     * @param runtimeContext
     */
    default void propertyColumnConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext){
        sqlPropertyConverter.sqlNativeSegment("{0}",c->c.expression(new SimpleSQLTableOwner(table),columnMetadata.getPropertyName()));
    }

    /**
     * insert update entity
     * update set
     * where
     *
     * @param table
     * @param columnMetadata
     * @param sqlParameter
     * @param sqlPropertyConverter
     * @param isCompareValue 当前值是用于比较还是存储
     */
    void valueConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLParameter sqlParameter, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext, boolean isCompareValue);
}
