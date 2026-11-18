package com.easy.query.oracle.migration;

import com.easy.query.core.basic.api.database.DefaultCodeFirstCommand;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.migration.MigrationCommand;

import java.util.List;

/**
 * create time 2025/1/26 08:51
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleCodeFirstCommand extends DefaultCodeFirstCommand {

    public OracleCodeFirstCommand(QueryRuntimeContext runtimeContext, List<MigrationCommand> migrationCommands) {
        super(runtimeContext, migrationCommands);
    }
}
