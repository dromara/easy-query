package com.easy.query.core.migration;

import java.util.List;

/**
 * create time 2025/1/11 13:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MigrationsSQLGenerator {
    MigrationInfoConverter createConverter(MigrationContext migrationContext);
    List<MigrationCommand> generateMigrationSQL(MigrationContext migrationContext);
}
