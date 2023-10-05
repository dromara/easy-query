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
    default SQLFunction ifNull(SQLTableOwner tableOwner, String property, Object def) {
        return ifNull(tableOwner.getTable(), property, def);
    }

    SQLFunction ifNull(TableAvailable table, String property, Object def);
}
