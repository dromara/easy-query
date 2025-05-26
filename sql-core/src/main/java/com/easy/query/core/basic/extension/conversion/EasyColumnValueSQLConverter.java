package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleSQLTableOwner;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * create time 2025/1/20 22:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyColumnValueSQLConverter implements ColumnValueSQLConverter {
    private final String sql;
    private final boolean realColumn;
    private final List<ExpArg> expArgs;

    public EasyColumnValueSQLConverter(String sql, boolean realColumn, List<ExpArg> expArgs) {
        this.sql = sql;
        this.realColumn = realColumn;
        this.expArgs = expArgs;
    }

    @Override
    public boolean isRealColumn() {
        return realColumn;
    }

    private void accept(TableAvailable table, SQLNativePropertyExpressionContext context) {
        for (ExpArg expArg : expArgs) {
            if (expArg.argType == ExpArgTypeEnum.PROPERTY) {
                context.expression(new SimpleSQLTableOwner(table), expArg.prop);
            } else if (expArg.argType == ExpArgTypeEnum.VALUE) {
                context.value(expArg.value);
            }
        }
    }

    @Override
    public void selectColumnConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext) {

        sqlPropertyConverter.sqlNativeSegment(sql, context -> {
            accept(table,context);
            context.setAlias(columnMetadata.getName());
        });
    }

    @Override
    public void propertyColumnConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext) {
        if (isRealColumn()) {
            sqlPropertyConverter.sqlNativeSegment("{0}",c->c.expression(new SimpleSQLTableOwner(table),columnMetadata.getPropertyName()));
        } else {
            sqlPropertyConverter.sqlNativeSegment(sql, context -> {
                accept(table,context);
            });
        }
    }

    @Override
    public void valueConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLParameter sqlParameter, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext, boolean isCompareValue) {
        sqlPropertyConverter.sqlNativeSegment("{0}", context -> {
            context.value(sqlParameter);
        });
    }
}
