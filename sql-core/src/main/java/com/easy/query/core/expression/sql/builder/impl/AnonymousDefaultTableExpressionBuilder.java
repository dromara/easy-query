package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author xuejiaming
 * @FileName: EasyAnonymousEntityTableExpressionSegment.java
 * @Description: 匿名实体表表达式
 * create time 2023/3/3 23:31
 */
public class AnonymousDefaultTableExpressionBuilder extends DefaultTableExpressionBuilder implements AnonymousEntityTableExpressionBuilder {
    protected final EntityQueryExpressionBuilder entityQueryExpressionBuilder;

    public AnonymousDefaultTableExpressionBuilder(TableAvailable entityTable, MultiTableTypeEnum multiTableType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        super(entityTable, multiTableType, entityQueryExpressionBuilder.getExpressionContext());
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
    }

    @Override
    public SQLActionExpression1<WherePredicate<Object>> getLogicDeleteQueryFilterExpression() {
        return null;
    }

    @Override
    public SQLActionExpression1<ColumnSetter<Object>> getLogicDeletedSQLExpression() {
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
    public void setTableNameAs(Function<String, String> tableNameAs) {
    }

    @Override
    public void setSchemaAs(Function<String, String> schemaAs) {
    }

    @Override
    public void setTableLinkAs(Function<String, String> linkAs) {
    }

    @Override
    public void setTableSegmentAs(BiFunction<String, String, String> segmentAs) {
    }

    @Override
    public void setTableLogicDelete(Supplier<Boolean> tableLogicDel) {

    }

    @Override
    public EntityTableExpressionBuilder copyEntityTableExpressionBuilder() {


        EntityTableExpressionBuilder anonymousTableExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createAnonymousEntityTableExpressionBuilder(entityTable, multiTableType, entityQueryExpressionBuilder.cloneEntityExpressionBuilder());
        if (on != null) {
            on.copyTo(anonymousTableExpressionBuilder.getOn());
        }
        anonymousTableExpressionBuilder.setTableLinkAs(this.linkAs);
        return anonymousTableExpressionBuilder;
    }

    @Override
    public EntityTableSQLExpression toExpression() {

        EntityTableSQLExpression anonymousTableSQLExpression = runtimeContext.getExpressionFactory().createAnonymousEntityTableSQLExpression(entityTable, multiTableType, entityQueryExpressionBuilder.toExpression(), runtimeContext);
        if (EasySQLSegmentUtil.isNotEmpty(on)) {
            anonymousTableSQLExpression.setOn(on.clonePredicateSegment());
        }
        anonymousTableSQLExpression.setTableNameAs(tableNameAs);
        anonymousTableSQLExpression.setSchemaAs(schemaAs);
        anonymousTableSQLExpression.setLinkAs(linkAs);
        return anonymousTableSQLExpression;
    }
}
