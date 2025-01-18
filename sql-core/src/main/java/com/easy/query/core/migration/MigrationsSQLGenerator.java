package com.easy.query.core.migration;

import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression3;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.List;

/**
 * create time 2025/1/11 13:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MigrationsSQLGenerator {
    List<MigrationCommand> generateMigrationSQL(MigrationContext migrationContext);
}
