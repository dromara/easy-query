package com.easy.query.test.conversion;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.expression.parser.core.base.core.SQLPropertyNative;

/**
 * create time 2023/8/7 17:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQLAesEncryptColumnValueSQLConverter implements ColumnValueSQLConverter {

    /**
     * 用于查询
     * @param propertyName
     * @param sqlPropertyNative
     */
    @Override
    public void columnConverter(String propertyName, SQLPropertyNative<Object> sqlPropertyNative) {
        sqlPropertyNative.sqlNativeSegment("AES_DECRYPT('{0}','{1}')",context->{
            context.expression(propertyName)
                    .value("1234567890123456");
        });
    }

    /**
     * select update set insert where
     * @param propertyName
     * @param val
     * @param sqlPropertyNative
     */
    @Override
    public void valueConverter(String propertyName, Object val, SQLPropertyNative<Object> sqlPropertyNative) {
        sqlPropertyNative.sqlNativeSegment("AES_ENCRYPT('{0}','{1}')",context->{
            context.value(val)
                    .value("1234567890123456");
        });
    }
}
