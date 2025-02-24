package com.easy.query.dameng.migration;

import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.CodeFirstCommandArg;
import com.easy.query.core.basic.api.database.CodeFirstCommandTxArg;
import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
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
public class DamengCodeFirstCommand implements CodeFirstCommand {
    private static final Log log= LogFactory.getLog(DamengCodeFirstCommand.class);
    private final QueryRuntimeContext runtimeContext;
    private final List<MigrationCommand> migrationCommands;

    public DamengCodeFirstCommand(QueryRuntimeContext runtimeContext, List<MigrationCommand> migrationCommands){
        this.runtimeContext = runtimeContext;
        this.migrationCommands = migrationCommands;
    }

    @Override
    public void executeWithEnvTransaction(Consumer<CodeFirstCommandArg> consumer) {
        StringBuilder sql = new StringBuilder();
        sql.append("BEGIN");
        for (MigrationCommand migrationCommand : migrationCommands) {
            sql.append(System.lineSeparator());
            sql.append(migrationCommand.toSQL());
        }
        sql.append(System.lineSeparator());
        sql.append("END;");
        String executeSQL = sql.toString();
        if(EasyStringUtil.isBlank(executeSQL)){
            log.info("execute sql is empty.");
            return;
        }
        consumer.accept(new CodeFirstCommandArg(executeSQL));
        SQLClientApiFactory sqlClientApiFactory = runtimeContext.getSQLClientApiFactory();
        long l = sqlClientApiFactory.createJdbcExecutor(runtimeContext).sqlExecute(executeSQL, Collections.emptyList());
    }

    @Override
    public void executeWithTransaction(Consumer<CodeFirstCommandTxArg> consumer) {

        StringBuilder sql = new StringBuilder();
        sql.append("BEGIN");
        for (MigrationCommand migrationCommand : migrationCommands) {
            sql.append(System.lineSeparator());
            sql.append(migrationCommand.toSQL());
        }
        sql.append(System.lineSeparator());
        sql.append("END;");
        String executeSQL = sql.toString();
        if(EasyStringUtil.isBlank(executeSQL)){
            log.info("execute sql is empty.");
            return;
        }
        ConnectionManager connectionManager = runtimeContext.getConnectionManager();
        SQLClientApiFactory sqlClientApiFactory = runtimeContext.getSQLClientApiFactory();
        try(Transaction transaction = connectionManager.beginTransaction()){
            CodeFirstCommandTxArg codeFirstCommandTxArg = new CodeFirstCommandTxArg(transaction, executeSQL);
            consumer.accept(codeFirstCommandTxArg);

            long l = sqlClientApiFactory.createJdbcExecutor(runtimeContext).sqlExecute(executeSQL, Collections.emptyList());
            codeFirstCommandTxArg.getTransactionConsumer().accept(transaction);
        }
    }
}
