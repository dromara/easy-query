package com.easy.query.core.migration;

import java.util.List;

/**
 * create time 2025/1/11 13:49
 * feature:<a href="https://github.com/dromara/easy-query/issues/12">code-first</a>
 *
 * @author xuejiaming
 */
public interface MigrationsSQLGenerator {
    List<MigrationCommand> generateMigrationSQL(MigrationContext migrationContext);
}
