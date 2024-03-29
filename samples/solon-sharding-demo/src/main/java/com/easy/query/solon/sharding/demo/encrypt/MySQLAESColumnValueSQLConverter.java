package com.easy.query.solon.sharding.demo.encrypt;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.SQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleSQLTableOwner;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/8/12 21:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQLAESColumnValueSQLConverter implements ColumnValueSQLConverter {
    /**
     * 数据加密秘钥
     */
    private static final String SECRET="1234567890123456";
    @Override
    public void selectColumnConvert(TableAvailable table, ColumnMetadata columnMetadata, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext) {
        sqlPropertyConverter.sqlNativeSegment("AES_DECRYPT(from_base64({0}),{1})",context->{
            context
                    .expression(columnMetadata.getPropertyName())//采用变量是因为可能出现join附带别名所以需要变量
                    .value(SECRET)
                    .setAlias(columnMetadata.getName());
        });
    }

    @Override
    public void propertyColumnConvert(TableAvailable table, ColumnMetadata columnMetadata, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext) {
        sqlPropertyConverter.sqlNativeSegment("{0}",c->c.expression(new SimpleSQLTableOwner(table),columnMetadata.getPropertyName()));
    }

    @Override
    public void valueConvert(TableAvailable table, ColumnMetadata columnMetadata, SQLParameter sqlParameter, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext,boolean isCompareValue) {
        sqlPropertyConverter.sqlNativeSegment("to_base64(AES_ENCRYPT({0},{1}))",context->{
            context.value(sqlParameter).value(SECRET);
        });
    }
}
