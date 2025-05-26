package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ColumnMetadata;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2023/8/8 22:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultColumnValueSQLConverter implements ColumnValueSQLConverter{
    @Override
    public boolean isRealColumn() {
        return true;
    }


    @Override
    public void selectColumnConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext) {

    }

    @Override
    public void propertyColumnConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext) {

    }

    @Override
    public void valueConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLParameter sqlParameter, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext, boolean isCompareValue) {

    }
}
