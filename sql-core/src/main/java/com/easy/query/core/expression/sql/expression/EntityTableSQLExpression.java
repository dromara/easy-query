package com.easy.query.core.expression.sql.expression;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.function.Function;

/**
 * create time 2023/4/22 21:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityTableSQLExpression extends SQLExpression {
    EntityMetadata getEntityMetadata();
    String getTableName();
    void setTableNameAs(Function<String, String> tableNameAs);

    void setSchemaAs(Function<String, String> schemaAs);
    void setLinkAs(Function<String, String> linkAs);


    boolean tableNameIsAs();
    PredicateSegment getOn();
    void setOn(PredicateSegment predicateSegment);
    TableAvailable getEntityTable();

    @Override
    EntityTableSQLExpression cloneSQLExpression();
}
