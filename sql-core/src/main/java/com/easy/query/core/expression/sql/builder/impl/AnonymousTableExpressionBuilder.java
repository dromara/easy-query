package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLColumnSetter;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.expression.TableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousTableSQLExpressionImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.SQLSegmentUtil;

/**
 * @author xuejiaming
 * @FileName: EasyAnonymousEntityTableExpressionSegment.java
 * @Description: 匿名实体表表达式
 * @Date: 2023/3/3 23:31
 */
public class AnonymousTableExpressionBuilder extends TableExpressionBuilder implements AnonymousEntityTableExpressionBuilder {
    private final EntityQueryExpressionBuilder entityQueryExpressionBuilder;

    public AnonymousTableExpressionBuilder(TableAvailable entityTable, MultiTableTypeEnum multiTableType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        super(entityTable, multiTableType,entityQueryExpressionBuilder.getRuntimeContext());
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
    }

    @Override
    public SQLExpression1<SQLWherePredicate<Object>> getLogicDeleteQueryFilterExpression() {
        return null;
    }

    @Override
    public SQLExpression1<SQLColumnSetter<Object>> getLogicDeletedSQLExpression() {
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
    public EntityTableExpressionBuilder copyEntityTableExpressionBuilder() {

        AnonymousTableExpressionBuilder anonymousTableExpressionBuilder = new AnonymousTableExpressionBuilder(entityTable, multiTableType, entityQueryExpressionBuilder.cloneEntityExpressionBuilder());
        if (on != null) {
            on.copyTo(anonymousTableExpressionBuilder.getOn());
        }
        anonymousTableExpressionBuilder.tableNameAs = this.tableNameAs;
        return anonymousTableExpressionBuilder;
    }

    @Override
    public TableSQLExpression toExpression() {

        AnonymousTableSQLExpressionImpl anonymousTableSQLExpression = new AnonymousTableSQLExpressionImpl(entityTable, multiTableType,entityQueryExpressionBuilder.toExpression(),runtimeContext);
        if(SQLSegmentUtil.isNotEmpty(on)){
            anonymousTableSQLExpression.setOn(on.clonePredicateSegment());
        }
        return anonymousTableSQLExpression;
    }
}
