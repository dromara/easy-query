package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousTableSqlExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.SqlSegmentUtil;

/**
 * @author xuejiaming
 * @FileName: EasyAnonymousEntityTableExpressionSegment.java
 * @Description: 匿名实体表表达式
 * @Date: 2023/3/3 23:31
 */
public class AnonymousTableExpressionBuilder extends TableExpressionBuilder implements AnonymousEntityTableExpressionBuilder {
    private final EntityQueryExpressionBuilder entityQueryExpressionBuilder;

    public AnonymousTableExpressionBuilder(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(entityMetadata, index, alias, multiTableType);
        this.entityQueryExpressionBuilder = sqlEntityExpression;
    }

    @Override
    public SqlExpression<SqlPredicate<Object>> getLogicDeleteQueryFilterExpression() {
        return null;
    }

    @Override
    public SqlExpression<SqlColumnSetter<Object>> getLogicDeletedSqlExpression() {
        return null;
    }

    @Override
    public EntityQueryExpressionBuilder getEntityQueryExpressionBuilder() {
        return entityQueryExpressionBuilder;
    }

    @Override
    public String getColumnName(String propertyName) {
        return getEntityMetadata().getColumnName(propertyName);
    }

    @Override
    public String getTableName() {
        if (tableNameAs != null) {
            return tableNameAs.apply(alias);
        }
        return alias;
    }

    @Override
    public EntityTableExpressionBuilder copyEntityTableExpressionBuilder() {

        AnonymousTableExpressionBuilder anonymousTableExpressionBuilder = new AnonymousTableExpressionBuilder(entityMetadata, index, alias, multiTableType, entityQueryExpressionBuilder.cloneEntityExpressionBuilder());
        if (on != null) {
            on.copyTo(anonymousTableExpressionBuilder.getOn());
        }
        anonymousTableExpressionBuilder.tableNameAs = this.tableNameAs;
        return anonymousTableExpressionBuilder;
    }

    @Override
    public EasyTableSqlExpression toExpression() {
        AnonymousTableSqlExpression anonymousTableSqlExpression = new AnonymousTableSqlExpression(entityMetadata, index, alias, multiTableType, (EasyQuerySqlExpression) entityQueryExpressionBuilder.toExpression());
        if(SqlSegmentUtil.isNotEmpty(on)){
            anonymousTableSqlExpression.setOn(on.clonePredicateSegment());
        }
        return anonymousTableSqlExpression;
    }
}
