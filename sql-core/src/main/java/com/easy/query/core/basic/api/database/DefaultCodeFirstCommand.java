package com.easy.query.core.basic.api.database;

import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.util.EasyStringUtil;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * create time 2025/1/26 08:51
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultCodeFirstCommand implements CodeFirstCommand {
    private static final Log log = LogFactory.getLog(DefaultCodeFirstCommand.class);
    private final QueryRuntimeContext runtimeContext;
    private final List<MigrationCommand> migrationCommands;

    public DefaultCodeFirstCommand(QueryRuntimeContext runtimeContext, List<MigrationCommand> migrationCommands) {
        this.runtimeContext = runtimeContext;
        this.migrationCommands = migrationCommands;
    }

    @Override
    public void forEach(Consumer<CommandTxArg> consumer) {
        SQLClientApiFactory sqlClientApiFactory = runtimeContext.getSQLClientApiFactory();
        boolean hasSQL = false;
        for (MigrationCommand migrationCommand : migrationCommands) {
            for (String sql : migrationCommand.getSQLList()) {
                if (EasyStringUtil.isNotBlank(sql)) {
                    hasSQL = true;
                    consumer.accept(new DefaultCommandTxArg(sql, s -> {
                        sqlClientApiFactory.createJdbcExecutor(runtimeContext).sqlExecute(s, Collections.emptyList());
                    }));
                }
            }
        }
        if (!hasSQL) {
            log.info("execute sql is empty.");
        }
    }
}
