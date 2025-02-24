package com.easy.query.dameng.migration;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DefaultDatabaseCodeFirst;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.migration.MigrationCommand;

import java.util.List;

/**
 * create time 2025/2/23 11:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengDatabaseCodeFirst extends DefaultDatabaseCodeFirst {

    public DamengDatabaseCodeFirst(ServiceProvider serviceProvider) {
        super(serviceProvider);
    }

    @Override
    public CodeFirstCommand createCodeFirstCommand(List<MigrationCommand> migrationCommands) {
        return new DamengCodeFirstCommand(getRuntimeContext(),migrationCommands);
    }
}
