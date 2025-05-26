package com.easy.query.test.conversion;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.SQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.metadata.ColumnMetadata;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2024/3/28 21:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class FullNameColumnValueSQLConverter implements ColumnValueSQLConverter {
    @Override
    public boolean isRealColumn() {
        return false;
    }

    @Override
    public void selectColumnConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext) {
        SQLFunc fx = runtimeContext.fx();
        SQLFunction concat = fx.concat("firstName", "lastName");
        String sqlSegment = concat.sqlSegment(table);
        sqlPropertyConverter.sqlNativeSegment(sqlSegment,context->{
            concat.consume(context.getSQLNativeChainExpressionContext());
            context.setAlias(columnMetadata.getName());
        });
    }

    @Override
    public void propertyColumnConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext) {
        SQLFunc fx = runtimeContext.fx();
        SQLFunction concat = fx.concat("firstName", "lastName");
        String sqlSegment = concat.sqlSegment(table);
        sqlPropertyConverter.sqlNativeSegment(sqlSegment,context->{
            concat.consume(context.getSQLNativeChainExpressionContext());
        });
    }

    @Override
    public void valueConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLParameter sqlParameter, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext, boolean isCompareValue) {
        sqlPropertyConverter.sqlNativeSegment("{0}",context->{
            context.value(sqlParameter);
        });
    }
}
