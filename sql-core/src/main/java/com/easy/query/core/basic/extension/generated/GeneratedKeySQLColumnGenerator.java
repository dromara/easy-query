package com.easy.query.core.basic.extension.generated;

import com.easy.query.core.basic.extension.conversion.SQLPropertyConverter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/8/24 13:34
 * 文件说明
 *
 * @author xuejiaming
 */
public interface GeneratedKeySQLColumnGenerator {
    void configure(TableAvailable table, ColumnMetadata columnMetadata, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext);
}
