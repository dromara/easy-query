package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/8/8 22:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultColumnValueSQLConverter implements ColumnValueSQLConverter{
    @Override
    public void columnConverter(TableAvailable table, ColumnMetadata columnMetadata, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext) {

    }

    @Override
    public void valueConverter(TableAvailable table, ColumnMetadata columnMetadata, SQLParameter sqlParameter, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext) {

    }
}
