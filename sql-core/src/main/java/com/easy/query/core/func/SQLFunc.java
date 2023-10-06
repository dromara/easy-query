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

    default SQLFunction dateTimeFormat(String property, String javaFormat) {
        return dateTimeFormat((TableAvailable) null, property, javaFormat);
    }

    default SQLFunction dateTimeFormat(SQLTableOwner tableOwner, String property, String javaFormat){
        return dateTimeFormat(tableOwner.getTable(), property, javaFormat);
    }

    SQLFunction dateTimeFormat(TableAvailable table, String property, String javaFormat);
}
