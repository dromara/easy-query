package com.easy.query.core.func;

import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/10/5 22:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFunc {
    default SQLFunction ifNull(String property, Object def) {
        return ifNull((TableAvailable) null, property, def);
    }

    default SQLFunction ifNull(SQLTableOwner tableOwner, String property, Object def) {
        return ifNull(tableOwner.getTable(), property, def);
    }

    SQLFunction ifNull(TableAvailable table, String property, Object def);

    default SQLFunction dateTimeJavaFormat(String property, String javaFormat) {
        return dateTimeJavaFormat((TableAvailable) null, property, javaFormat);
    }

    default SQLFunction dateTimeJavaFormat(SQLTableOwner tableOwner, String property, String javaFormat){
        return dateTimeJavaFormat(tableOwner.getTable(), property, javaFormat);
    }

    SQLFunction dateTimeJavaFormat(TableAvailable table, String property, String javaFormat);
    default SQLFunction dateTimeSQLFormat(String property, String format) {
        return dateTimeSQLFormat((TableAvailable) null, property, format);
    }

    default SQLFunction dateTimeSQLFormat(SQLTableOwner tableOwner, String property, String format){
        return dateTimeSQLFormat(tableOwner.getTable(), property, format);
    }

    SQLFunction dateTimeSQLFormat(TableAvailable table, String property, String format);
}
