package com.easy.query.test.conversion;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.SQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleSQLTableOwner;
import com.easy.query.core.metadata.ColumnMetadata;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2023/8/7 17:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQLAesEncryptColumnValueSQLConverter implements ColumnValueSQLConverter {
    /**
     * 数据加密秘钥
     */
    private static final String SECRET="1234567890123456";

    @Override
    public boolean isRealColumn() {
        return true;
    }

    @Override
    public void selectColumnConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext) {
//        Dialect dialect = runtimeContext.getQueryConfiguration().getDialect();
        sqlPropertyConverter.sqlNativeSegment("AES_DECRYPT(from_base64({0}),{1})",context->{
            context
                    .expression(columnMetadata.getPropertyName())//采用变量是因为可能出现join附带别名所以需要变量
                    .value(SECRET)
                    .setAlias(columnMetadata.getName());
            //.constValue(dialect.getQuoteName(columnMetadata.getName()));//如果这边也是用变量就会导致join下不是别名而是带具体表的列比如:t.`phone`
        });
    }

    @Override
    public void propertyColumnConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext) {
        sqlPropertyConverter.sqlNativeSegment("{0}",c->c.expression(new SimpleSQLTableOwner(table),columnMetadata.getPropertyName()));
    }

    @Override
    public void valueConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLParameter sqlParameter, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext, boolean isCompareValue) {
        sqlPropertyConverter.sqlNativeSegment("to_base64(AES_ENCRYPT({0},{1}))",context->{
            context.value(sqlParameter).value(SECRET);
        });
    }
}
