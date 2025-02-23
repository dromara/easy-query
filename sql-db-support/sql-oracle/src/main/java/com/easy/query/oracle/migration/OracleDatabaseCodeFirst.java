package com.easy.query.oracle.migration;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DefaultDatabaseCodeFirst;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.migration.MigrationCommand;

import java.util.List;

/**
 * create time 2025/2/23 11:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleDatabaseCodeFirst extends DefaultDatabaseCodeFirst {

    public OracleDatabaseCodeFirst(ServiceProvider serviceProvider) {
        super(serviceProvider);
    }

    @Override
    public CodeFirstCommand createCodeFirstCommand(List<MigrationCommand> migrationCommands) {
        return new OracleCodeFirstCommand(getRuntimeContext(),migrationCommands);
    }
}
