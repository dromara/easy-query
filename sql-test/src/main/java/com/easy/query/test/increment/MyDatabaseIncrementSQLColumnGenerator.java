package com.easy.query.test.increment;

import com.easy.query.core.basic.extension.conversion.SQLPropertyConverter;
import com.easy.query.core.basic.extension.increment.IncrementSQLColumnGenerator;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/8/9 10:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyDatabaseIncrementSQLColumnGenerator implements IncrementSQLColumnGenerator {
    @Override
    public void configure(TableAvailable table, ColumnMetadata columnMetadata, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext) {
        sqlPropertyConverter.sqlNativeSegment("mysqlNextId()");
    }
}
