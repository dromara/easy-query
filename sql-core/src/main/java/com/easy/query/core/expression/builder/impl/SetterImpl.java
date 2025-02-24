package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.Setter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.segment.impl.InsertUpdateColumnConfigureSegment2Impl;
import com.easy.query.core.expression.segment.impl.InsertUpdateColumnConfigureSegmentImpl;
import com.easy.query.core.expression.segment.impl.UpdateColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.UpdateColumnSetSegmentImpl;
import com.easy.query.core.expression.segment.impl.UpdateColumnSetSelfSegmentImpl;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.SQLFunctionTranslateImpl;

import java.util.Objects;

/**
 * create time 2023/12/8 10:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class SetterImpl implements Setter {
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected final SQLBuilderSegment sqlBuilderSegment;
    protected final QueryRuntimeContext runtimeContext;
    protected final SQLSegmentFactory sqlSegmentFactory;

    public SetterImpl(EntityExpressionBuilder entityExpressionBuilder, SQLBuilderSegment sqlBuilderSegment) {

        this.entityExpressionBuilder = entityExpressionBuilder;
        this.runtimeContext = entityExpressionBuilder.getRuntimeContext();
        this.sqlSegmentFactory = runtimeContext.getSQLSegmentFactory();
        this.sqlBuilderSegment = sqlBuilderSegment;
    }

    @Override
    public SQLBuilderSegment getSQLBuilderSegment() {
        return sqlBuilderSegment;
    }

    @Override
    public Setter set(boolean condition, TableAvailable table, String property, Object val) {
        if (condition) {
            InsertUpdateSetColumnSQLSegment updateSetColumnSegment = sqlSegmentFactory.createUpdateSetColumnSegment(table, property, entityExpressionBuilder.getExpressionContext(), val);

            sqlBuilderSegment.append(updateSetColumnSegment);
        }
        return this;
    }

    @Override
    public Setter setWithColumn(boolean condition, TableAvailable table, String property1, String property2) {
        if (condition) {
            InsertUpdateSetColumnSQLSegment updateSetSelfColumnSegment = sqlSegmentFactory.createUpdateSetSelfColumnSegment(table, property1, table, property2, entityExpressionBuilder.getExpressionContext());
            sqlBuilderSegment.append(updateSetSelfColumnSegment);
        }
        return this;
    }

    @Override
    public Setter setWithColumn(boolean condition, TableAvailable table1, String property1, TableAvailable table2, String property2) {
        if (condition) {
            InsertUpdateSetColumnSQLSegment updateSetSelfColumnSegment = sqlSegmentFactory.createUpdateSetSelfColumnSegment(table1, property1, table2, property2, entityExpressionBuilder.getExpressionContext());
            sqlBuilderSegment.append(updateSetSelfColumnSegment);
        }
        return this;
    }

    private void setSelf(boolean increment, TableAvailable table, String property, Number val) {

        InsertUpdateSetColumnSQLSegment columnWithSelfSegment = sqlSegmentFactory.createColumnWithSelfSegment(increment, table, property, val, entityExpressionBuilder.getExpressionContext());
        sqlBuilderSegment.append(columnWithSelfSegment);
    }

    @Override
    public Setter setIncrementNumber(boolean condition, TableAvailable table, String property, Number val) {

        if (condition) {
            setSelf(true, table, property, val);
        }
        return this;
    }

    @Override
    public Setter setDecrementNumber(boolean condition, TableAvailable table, String property, Number val) {
        if (condition) {
            setSelf(false, table, property, val);
        }
        return this;
    }

    @Override
    public Setter sqlNativeSegment(TableAvailable table, String property, String sqlSegment, SQLExpression1<SQLNativeExpressionContext> contextConsume) {
        Objects.requireNonNull(contextConsume, "sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(entityExpressionBuilder.getExpressionContext(), runtimeContext);
//        sqlNativeExpressionContext.expression(table,property);
        contextConsume.apply(sqlNativeExpressionContext);
        InsertUpdateSetColumnSQLSegment updateColumnSegment = sqlSegmentFactory.createUpdateColumnSegment(table, property, entityExpressionBuilder.getExpressionContext(), null);
        InsertUpdateColumnConfigureSegmentImpl insertUpdateColumnConfigureSegment = new InsertUpdateColumnConfigureSegmentImpl(updateColumnSegment, entityExpressionBuilder.getExpressionContext(), sqlSegment, sqlNativeExpressionContext);
        sqlBuilderSegment.append(insertUpdateColumnConfigureSegment);
        return this;
    }

    @Override
    public Setter sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativeExpressionContext> contextConsume) {

        Objects.requireNonNull(contextConsume, "sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(entityExpressionBuilder.getExpressionContext(), runtimeContext);
        contextConsume.apply(sqlNativeExpressionContext);
        SQLNativeSegment columnSegment = sqlSegmentFactory.createSQLNativeSegment(entityExpressionBuilder.getExpressionContext(), sqlSegment, sqlNativeExpressionContext);
        sqlBuilderSegment.append(columnSegment);
        return this;
    }

    @Override
    public Setter setFunc(TableAvailable table, String property, SQLFunction sqlFunction) {

        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(entityExpressionBuilder.getExpressionContext(),runtimeContext);
//        sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table,sqlNativeExpressionContext));
//        sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table,sqlNativeExpressionContext));

        SQLSegment sqlSegment = new SQLFunctionTranslateImpl(sqlFunction)
                .toSQLSegment(entityExpressionBuilder.getExpressionContext(), table, runtimeContext, null);
//        if (sqlFunction instanceof SQLLazyFunction) {
//            SQLLazyFunction sqlLazyFunction = (SQLLazyFunction) sqlFunction;
//            nextPredicateSegment.setPredicate(new SQLNativeLazyPredicateImpl(expressionContext, sqlLazyFunction, sqlSegment -> sqlSegment + " " + sqlPredicateAssert.getSQL(), sqlNativeExpressionContext));
//        } else {
//            String sqlSegment = sqlFunction.sqlSegment(table);
//            InsertUpdateColumnConfigureSegmentImpl insertUpdateColumnConfigureSegment = new InsertUpdateColumnConfigureSegmentImpl(new UpdateColumnSegmentImpl(table, property, entityExpressionBuilder.getExpressionContext()), entityExpressionBuilder.getExpressionContext(), sqlSegment, sqlNativeExpressionContext);
//            sqlBuilderSegment.append(insertUpdateColumnConfigureSegment);
//        }
//        String sqlSegment = sqlFunction.sqlSegment(table);
        InsertUpdateSetColumnSQLSegment updateColumnSegment = sqlSegmentFactory.createUpdateColumnSegment(table, property, entityExpressionBuilder.getExpressionContext(), null);
        InsertUpdateColumnConfigureSegment2Impl insertUpdateColumnConfigureSegment = new InsertUpdateColumnConfigureSegment2Impl(updateColumnSegment, entityExpressionBuilder.getExpressionContext(), sqlSegment, sqlNativeExpressionContext);
        sqlBuilderSegment.append(insertUpdateColumnConfigureSegment);

        return this;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }
}
