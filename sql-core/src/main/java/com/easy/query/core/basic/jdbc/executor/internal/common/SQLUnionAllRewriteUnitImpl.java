package com.easy.query.core.basic.jdbc.executor.internal.common;

import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyToSQLUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/5/19 10:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLUnionAllRewriteUnitImpl implements SQLRewriteUnit {
    private final Map<TableAvailable, List<String>> tableNameRewriteMap;

    public SQLUnionAllRewriteUnitImpl(Map<TableAvailable, List<String>> tableNameRewriteMap) {

        this.tableNameRewriteMap = tableNameRewriteMap;
    }

    @Override
    public void rewriteTableName(EntityTableSQLExpression entityTableSQLExpression) {
        if (tableNameRewriteMap == null) {
            return;
        }
        List<String> realTableNames = tableNameRewriteMap.get(entityTableSQLExpression.getEntityTable());
        if (realTableNames == null) {
            return;
        }
        if (EasyCollectionUtil.isSingle(realTableNames)) {
            String actualName = EasyCollectionUtil.first(realTableNames);
            entityTableSQLExpression.setTableNameAs(o -> actualName);
        } else {


            StringBuilder tableSQL = new StringBuilder().append("(");
            boolean first = true;
            Collections.sort(realTableNames);
            for (String realTableName : realTableNames) {
                EntityMetadata entityMetadata = entityTableSQLExpression.getEntityMetadata();
                SQLKeyword sqlKeyword = entityTableSQLExpression.getQueryRuntimeContext().getQueryConfiguration().getDialect();
                String schemaTableName = EasyToSQLUtil.getSchemaTableName(sqlKeyword, entityMetadata, realTableName, entityTableSQLExpression.getSchemaAs(), null);
                if (!first) {
                    tableSQL.append(" UNION ALL ");
                }
                tableSQL.append("SELECT * FROM ").append(schemaTableName);
                first = false;
            }
            tableSQL.append(")");
            String actualName = tableSQL.toString();
            entityTableSQLExpression.setTableSegmentAs((tableName, alias) -> actualName + " " + alias);
        }

    }
}
