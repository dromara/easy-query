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


    /**
     * select查询
     * @param table
     * @param columnMetadata
     * @param sqlPropertyConverter
     */
    void selectConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext);

    /**
     * 当前属性如何转成对应的列来使用
     * @param table
     * @param columnMetadata
     * @param sqlPropertyConverter
     * @param runtimeContext
     */
    default void propertyColumnConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext){
        sqlPropertyConverter.sqlNativeSegment("{0}",c->c.expression(new SimpleSQLTableOwner(table),columnMetadata.getName()));
    }

    /**
     * insert update entity
     * update set
     * where
     * @param table
     * @param columnMetadata
     * @param sqlParameter
     * @param sqlPropertyConverter
     */
    void valueConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata,@NotNull SQLParameter sqlParameter,@NotNull SQLPropertyConverter sqlPropertyConverter,@NotNull QueryRuntimeContext runtimeContext);
}
