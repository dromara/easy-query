package com.easy.query.core.expression.sql.expression;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * create time 2023/4/22 21:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityTableSQLExpression extends SQLExpression {
    QueryRuntimeContext getQueryRuntimeContext();
    EntityMetadata getEntityMetadata();
    void setMultiTableType(MultiTableTypeEnum multiTableType);
    String getTableName();
    void setTableNameAs(Function<String, String> tableNameAs);
    Function<String, String> getTableNameAs();
    void setSchemaAs(Function<String, String> schemaAs);
    Function<String, String> getSchemaAs();
    void setLinkAs(Function<String, String> linkAs);
    void setTableSegmentAs(BiFunction<String, String, String> segmentAs);
    boolean tableNameIsAs();
    PredicateSegment getOn();
    void setOn(PredicateSegment predicateSegment);
    TableAvailable getEntityTable();

    @Override
    EntityTableSQLExpression cloneSQLExpression();
}
