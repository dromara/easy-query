package com.easy.query.core.migration.commands;

import com.easy.query.core.migration.MigrationCommand;

import java.util.Collections;
import java.util.List;

public class MultiMigrationCommand implements MigrationCommand {
    private final List<String> statements;

    public MultiMigrationCommand(String statement) {
        this(Collections.singletonList(statement));
    }

    public MultiMigrationCommand(List<String> statements) {
        this.statements = statements;
    }

    public List<String> getStatements() {
        return statements;
    }

    @Override
    public List<String> getSQLList() {
        return statements;
    }
}
