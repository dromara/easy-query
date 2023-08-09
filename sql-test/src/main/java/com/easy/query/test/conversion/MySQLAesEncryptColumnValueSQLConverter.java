package com.easy.query.test.conversion;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.SQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.configuration.dialect.Dialect;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/8/7 17:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQLAesEncryptColumnValueSQLConverter implements ColumnValueSQLConverter {
    private static final String SECRET="1234567890123456";
    @Override
    public void columnConverter(TableAvailable table, ColumnMetadata columnMetadata, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext) {
        Dialect dialect = runtimeContext.getQueryConfiguration().getDialect();
        sqlPropertyConverter.sqlNativeSegment("AES_DECRYPT(from_base64({0}),{1}) AS {2}",context->{
           context
                   .expression(columnMetadata.getPropertyName())//采用变量是因为可能出现join附带别名所以需要变量
                   .value(SECRET)
                   .constValue(dialect.getQuoteName(columnMetadata.getName()));//如果这边也是用变量就会导致join下不是别名而是带具体表的列比如:t.`phone`
        });
    }

    @Override
    public void valueConverter(TableAvailable table, ColumnMetadata columnMetadata, SQLParameter sqlParameter, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext) {
        sqlPropertyConverter.sqlNativeSegment("to_base64(AES_ENCRYPT({0},{1}))",context->{
            context.value(sqlParameter).value(SECRET);
        });
    }

//
//    @Override
//    public SQLExpression1<SQLPropertyNative<Object>> columnConverter(String propertyName, SQLPropertyNative<Object> sqlPropertyNative) {
//
//        sqlPropertyNative.sqlNativeSegment("AES_DECRYPT('{0}','{1}')",context->{
//            context.expression(propertyName)
//                    .value("1234567890123456");
//        });
//        return o->o.sqlNativeSegment("AES_DECRYPT('{0}','{1}')",context->{
//            context.expression(propertyName)
//                    .value("1234567890123456");
//        });
//    }
//
//    /**
//     * select update set insert where
//     * @param propertyName
//     * @param val
//     * @param sqlPropertyNative
//     */
//    @Override
//    public void valueConverter(String propertyName, Object val, SQLPropertyNative<Object> sqlPropertyNative) {
//        sqlPropertyNative.sqlNativeSegment("AES_ENCRYPT('{0}','{1}')",context->{
//            context.value(val)
//                    .value("1234567890123456");
//        });
//    }
//
//    @Override
//    public void sqlParameterConverter(String propertyName, SQLParameter sqlParameter) {
//        Object x=(property,sqlParameter1)->{
//
//        };
//    }
}
