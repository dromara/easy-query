package com.easy.query.core.migration.commands;

import com.easy.query.core.migration.MigrationCommand;

import java.util.List;

public class MultiMigrationCommand implements MigrationCommand {
    private final List<String> statements;

    public MultiMigrationCommand(List<String> statements) {
        this.statements = statements;
    }

    public List<String> getStatements() {
        return statements;
    }

    @Override
    public String toSQL() {
        StringBuilder sql = new StringBuilder();
        for (String statement : statements) {
            if (statement == null || statement.trim().isEmpty()) {
                continue;
            }
            if (sql.length() > 0) {
                sql.append(System.lineSeparator());
            }
            sql.append(statement);
            if (sql.charAt(sql.length() - 1) != ';') {
                sql.append(";");
            }
        }
        return sql.toString();
    }

    @Override
    public List<String> toSQLList() {
        return statements;
    }
}
