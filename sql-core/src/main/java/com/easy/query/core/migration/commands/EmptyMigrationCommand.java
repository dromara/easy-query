package com.easy.query.core.migration.commands;

import com.easy.query.core.migration.MigrationCommand;

import java.util.Collections;
import java.util.List;

/**
 * create time 2026/5/11 09:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class EmptyMigrationCommand implements MigrationCommand {
    @Override
    public List<String> getSQLList() {
        return Collections.emptyList();
    }
}
