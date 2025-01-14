package com.easy.query.test;

import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.MigrationContext;
import com.easy.query.core.migration.MigrationsSQLGenerator;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/1/14 14:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class MigrationTest extends BaseTest{

    @Test
    public void createTableSQLTest1(){
        MigrationsSQLGenerator migrationsSQLGenerator = easyEntityQuery.getRuntimeContext().getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(Arrays.asList(Topic.class, BlogEntity.class),easyEntityQuery.getRuntimeContext());
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateMigrationSQL(migrationContext);
        for (MigrationCommand migrationCommand : migrationCommands) {
            System.out.println(migrationCommand.toSQL());
        }
    }
}
