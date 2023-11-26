package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
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
    void columnConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata,@NotNull SQLPropertyConverter sqlPropertyConverter,@NotNull QueryRuntimeContext runtimeContext);

    /**
     * insert update entity
     * udpate set
     * where
     * @param table
     * @param columnMetadata
     * @param sqlParameter
     * @param sqlPropertyConverter
     */
    void valueConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata,@NotNull SQLParameter sqlParameter,@NotNull SQLPropertyConverter sqlPropertyConverter,@NotNull QueryRuntimeContext runtimeContext);
}
