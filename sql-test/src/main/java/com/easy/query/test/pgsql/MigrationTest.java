package com.easy.query.test.pgsql;

import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.MigrationContext;
import com.easy.query.core.migration.MigrationsSQLGenerator;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.MyMigrationBlog;
import com.easy.query.test.entity.Topic;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * create time 2025/1/21 17:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class MigrationTest extends PgSQLBaseTest{
    @Test
    public void test1(){

        MigrationsSQLGenerator migrationsSQLGenerator = entityQuery.getRuntimeContext().getMigrationsSQLGenerator();
        List<TableMigrationData> tableMigrationDataList = Arrays.asList(Topic.class, BlogEntity.class, MyMigrationBlog.class).stream()
                .map(o -> migrationsSQLGenerator.parseEntity(o)).collect(Collectors.toList());
        MigrationContext migrationContext = new MigrationContext(tableMigrationDataList);
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateMigrationSQL(migrationContext);
        for (MigrationCommand migrationCommand : migrationCommands) {
            System.out.println(migrationCommand.toSQL());
        }
//        Assert.assertEquals(1,migrationCommands.size());
//        MigrationCommand migrationCommand = migrationCommands.get(0);
    }
}
