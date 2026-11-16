package com.easy.query.core.migration;

import java.util.Collections;
import java.util.List;

/**
 * create time 2025/1/11 13:51
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MigrationCommand {
    String toSQL();

    default List<String> toSQLList() {
        return Collections.singletonList(toSQL());
    }
}
