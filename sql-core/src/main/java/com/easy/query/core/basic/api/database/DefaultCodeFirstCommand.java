package com.easy.query.core.basic.api.database;

import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.commands.MultiMigrationCommand;
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
    private final boolean executeIndividually;

    public DefaultCodeFirstCommand(QueryRuntimeContext runtimeContext, List<MigrationCommand> migrationCommands) {
        this(runtimeContext, migrationCommands, false);
    }

    protected DefaultCodeFirstCommand(QueryRuntimeContext runtimeContext, List<MigrationCommand> migrationCommands, boolean executeIndividually) {
        this.runtimeContext = runtimeContext;
        this.migrationCommands = migrationCommands;
        this.executeIndividually = executeIndividually;
    }


    @Override
    public void commands(Consumer<CommandTxArg> consumer) {

        String executeSQL = getExecuteSQL();
        if (EasyStringUtil.isBlank(executeSQL)) {
            log.info("execute sql is empty.");
            return;
        }
        execute(executeSQL, consumer);
    }

    private String getExecuteSQL() {
        boolean executeStatementsIndividually = isExecuteIndividually();
        StringBuilder sql = new StringBuilder();
        for (MigrationCommand migrationCommand : migrationCommands) {
            String commandSQL = migrationCommand.toSQL();
            sql.append(System.lineSeparator());
            sql.append(commandSQL);
            if (executeStatementsIndividually && EasyStringUtil.isNotBlank(commandSQL) && !commandSQL.trim().endsWith(";")) {
                sql.append(";");
            }
        }
        return sql.toString();
    }

    private void execute(String executeSQL, Consumer<CommandTxArg> consumer) {
        SQLClientApiFactory sqlClientApiFactory = runtimeContext.getSQLClientApiFactory();
        if (!isExecuteIndividually()) {
            consumer.accept(new DefaultCommandTxArg(executeSQL, s -> {
                sqlClientApiFactory.createJdbcExecutor(runtimeContext).sqlExecute(s, Collections.emptyList());
            }));
            return;
        }
        for (MigrationCommand migrationCommand : migrationCommands) {
            for (String sql : migrationCommand.toSQLList()) {
                if (EasyStringUtil.isNotBlank(sql)) {
                    consumer.accept(new DefaultCommandTxArg(sql, s -> {
                        sqlClientApiFactory.createJdbcExecutor(runtimeContext).sqlExecute(s, Collections.emptyList());
                    }));

                }
            }
        }
    }

    private boolean isExecuteIndividually() {
        return executeIndividually || migrationCommands.stream().anyMatch(MultiMigrationCommand.class::isInstance);
    }
}
