package com.easy.query.core.basic.extension.increment;

import com.easy.query.core.basic.extension.conversion.SQLPropertyConverter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/8/9 08:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface IncrementSQLColumnGenerator {
    void configure(TableAvailable table, ColumnMetadata columnMetadata, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext);
}
