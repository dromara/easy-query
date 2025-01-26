package com.easy.query.core.basic.api.database;

import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.migration.MigrationCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * create time 2025/1/26 08:51
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultCodeFirstExecutable implements CodeFirstExecutable {
    private final QueryRuntimeContext runtimeContext;
    private final List<MigrationCommand> migrationCommands;

    public DefaultCodeFirstExecutable(QueryRuntimeContext runtimeContext,List<MigrationCommand> migrationCommands){
        this.runtimeContext = runtimeContext;
        this.migrationCommands = migrationCommands;
    }

    @Override
    public void execute(Consumer<CodeFirstCommandArg> consumer) {
        StringBuilder sql = new StringBuilder();
        for (MigrationCommand migrationCommand : migrationCommands) {
            sql.append(System.lineSeparator());
            sql.append(migrationCommand.toSQL());
        }
        consumer.accept(new CodeFirstCommandArg(sql.toString()));
        String executeSQL = sql.toString();
        SQLClientApiFactory sqlClientApiFactory = runtimeContext.getSQLClientApiFactory();
        long l = sqlClientApiFactory.createJdbcExecutor(runtimeContext).sqlExecute(executeSQL, Collections.emptyList());
    }

    @Override
    public void executeWithTransaction(Consumer<CodeFirstCommandTxArg> consumer) {

        StringBuilder sql = new StringBuilder();
        for (MigrationCommand migrationCommand : migrationCommands) {
            sql.append(System.lineSeparator());
            sql.append(migrationCommand.toSQL());
        }
        ConnectionManager connectionManager = runtimeContext.getConnectionManager();
        SQLClientApiFactory sqlClientApiFactory = runtimeContext.getSQLClientApiFactory();
        try(Transaction transaction = connectionManager.beginTransaction()){
            String executeSQL = sql.toString();
            ArrayList<Consumer<Transaction>> consumers = new ArrayList<>();
            consumer.accept(new CodeFirstCommandTxArg(transaction,executeSQL,consumers));

            long l = sqlClientApiFactory.createJdbcExecutor(runtimeContext).sqlExecute(executeSQL, Collections.emptyList());
            consumers.forEach(c->c.accept(transaction));
        }
    }
}
