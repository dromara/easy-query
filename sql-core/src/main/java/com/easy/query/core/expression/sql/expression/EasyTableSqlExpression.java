package com.easy.query.core.expression.sql.expression;

import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.function.Function;

/**
 * create time 2023/4/22 21:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyTableSqlExpression extends EasySqlExpression{
    EntityMetadata getEntityMetadata();
    String getTableName();
    void setTableNameAs(Function<String,String> tableNameAs);
    boolean tableNameIsAs();
    PredicateSegment getOn();
    void setOn(PredicateSegment predicateSegment);
    String getAlias();
    int getIndex();

    @Override
    EasyTableSqlExpression cloneSqlExpression();
}
