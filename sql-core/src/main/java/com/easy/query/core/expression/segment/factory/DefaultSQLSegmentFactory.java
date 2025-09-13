package com.easy.query.core.expression.segment.factory;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.version.VersionStrategy;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.Column2SegmentImpl;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.ColumnValue2Segment;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.segment.OrderFuncColumnSegment;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.SelectConstSegment;
import com.easy.query.core.expression.segment.SelectCountDistinctSegment;
import com.easy.query.core.expression.segment.SubQueryColumnSegment;
import com.easy.query.core.expression.segment.impl.AnonymousColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.ColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.ColumnWithSelfSegmentImpl;
import com.easy.query.core.expression.segment.impl.GroupBySQLNativeSegmentImpl;
import com.easy.query.core.expression.segment.impl.GroupColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.InsertColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.InsertMapColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.OrderBySQLNativeSegment2Impl;
import com.easy.query.core.expression.segment.impl.OrderBySQLNativeSegmentImpl;
import com.easy.query.core.expression.segment.impl.SQLColumnAsSegmentImpl;
import com.easy.query.core.expression.segment.impl.SQLNativeSegmentImpl;
import com.easy.query.core.expression.segment.impl.SelectConstSegmentImpl;
import com.easy.query.core.expression.segment.impl.SelectCountDistinctSegmentImpl;
import com.easy.query.core.expression.segment.impl.SubQueryColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.UpdateColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.UpdateColumnSetSegmentImpl;
import com.easy.query.core.expression.segment.impl.UpdateColumnSetSelfSegmentImpl;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyColumnSegmentUtil;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/5/30 12:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLSegmentFactory implements SQLSegmentFactory {
    @Override
    public SelectConstSegment createSelectConstSegment(String projects) {
        return new SelectConstSegmentImpl(projects);
    }

    @Override
    public SelectCountDistinctSegment createSelectCountDistinctSegment(Collection<SQLSegment> sqlSegments) {
        return new SelectCountDistinctSegmentImpl(sqlSegments);
    }

    @Override
    public ColumnSegment createSelectColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, String alias) {
        Column2Segment selectColumn2Segment = EasyColumnSegmentUtil.createSelectColumn2Segment(table, columnMetadata, expressionContext, alias);
        return new ColumnSegmentImpl(selectColumn2Segment, alias);
    }

    @Override
    public ColumnSegment createAnonymousColumnSegment(TableAvailable table, ExpressionContext expressionContext, String alias) {
        return new AnonymousColumnSegmentImpl(table, expressionContext, alias);
    }

    @Override
    public SQLNativeSegment createSQLNativeSegment(ExpressionContext expressionContext, String columnConst, SQLNativeExpression sqlNativeExpression) {
        return new SQLNativeSegmentImpl(expressionContext, columnConst, sqlNativeExpression);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment createInsertColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext) {
//        Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, expressionContext);
        Column2Segment column2Segment = new Column2SegmentImpl(table, columnMetadata, expressionContext);
        ColumnValue2Segment columnValue2Segment = EasyColumnSegmentUtil.createColumnValue2Segment(table, columnMetadata, expressionContext, null);
        return new InsertColumnSegmentImpl(column2Segment, columnValue2Segment);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment createInsertMapColumnSegment(String columnName, String mapKey, QueryRuntimeContext runtimeContext) {
        return new InsertMapColumnSegmentImpl(columnName, mapKey, runtimeContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment createColumnWithSelfSegment(boolean increment, TableAvailable table, String propertyName, Object val, ExpressionContext expressionContext) {
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
        Column2Segment columnLeftSegment = new Column2SegmentImpl(table, columnMetadata, expressionContext);
        Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, expressionContext);
        return new ColumnWithSelfSegmentImpl(increment,columnLeftSegment, column2Segment, val);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment createUpdateColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, VersionStrategy versionStrategy) {

        Column2Segment column2Segment = new Column2SegmentImpl(table, columnMetadata, expressionContext);
        ColumnValue2Segment versionColumnValue2Segment = EasyColumnSegmentUtil.createColumnValue2Segment(table, columnMetadata, expressionContext, versionStrategy);
        return new UpdateColumnSegmentImpl(column2Segment, versionColumnValue2Segment);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment createUpdateColumnNullSegment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext) {

        Column2Segment column2Segment = new Column2SegmentImpl(table, columnMetadata, expressionContext);
        ColumnValue2Segment versionColumnValue2Segment = EasyColumnSegmentUtil.createColumnNullValue2Segment(table, columnMetadata, expressionContext);
        return new UpdateColumnSegmentImpl(column2Segment, versionColumnValue2Segment);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment createUpdateSetColumnSegment(TableAvailable table, String propertyName, ExpressionContext expressionContext, Object val) {
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
        Column2Segment column2Segment = new Column2SegmentImpl(table, columnMetadata, expressionContext);
//        ColumnValue2Segment columnValue2Segment = EasyColumnSegmentUtil.createColumnValue2Segment(table, columnMetadata, expressionContext, null);
        ColumnValue2Segment compareValue2Segment = EasyColumnSegmentUtil.createColumnCompareValue2Segment(table, columnMetadata, expressionContext, val, false, false);
        return new UpdateColumnSetSegmentImpl(column2Segment, compareValue2Segment);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment createUpdateSetSelfColumnSegment(TableAvailable leftTable, String leftPropertyName, TableAvailable rightTable, String rightPropertyName, ExpressionContext expressionContext) {
        ColumnMetadata leftColumnMetadata = leftTable.getEntityMetadata().getColumnNotNull(leftPropertyName);
        ColumnMetadata rightColumnMetadata = rightTable.getEntityMetadata().getColumnNotNull(rightPropertyName);
        Column2Segment leftColumn2Segment = EasyColumnSegmentUtil.createColumn2Segment(leftTable, leftColumnMetadata, expressionContext);
        Column2Segment rightColumn2Segment = EasyColumnSegmentUtil.createColumn2Segment(rightTable, rightColumnMetadata, expressionContext);
        return new UpdateColumnSetSelfSegmentImpl(leftColumn2Segment, rightColumn2Segment);
    }

    @Override
    public GroupByColumnSegment createGroupByColumnSegment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext) {
        Column2Segment selectColumn2Segment = EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, expressionContext);
        return new GroupColumnSegmentImpl(selectColumn2Segment);
    }

    @Override
    public OrderBySegment createOrderBySQLNativeSegment(ExpressionContext expressionContext, String sqlSegment, SQLNativeExpression sqlNativeExpression, boolean asc) {
        return new OrderBySQLNativeSegmentImpl(expressionContext, sqlSegment, sqlNativeExpression, asc);
    }

    @Override
    public OrderBySegment createOrderBySQLNativeSegment2(ExpressionContext expressionContext, SQLSegment sqlSegment, Function<String, String> sqlSegmentFunction, SQLNativeExpression sqlNativeExpression, boolean asc) {
        return new OrderBySQLNativeSegment2Impl(expressionContext, sqlSegment, sqlSegmentFunction, sqlNativeExpression, asc);
    }

    @Override
    public GroupByColumnSegment createGroupBySQLNativeSegment(ExpressionContext expressionContext, String sqlSegment, SQLNativeExpression sqlNativeExpression) {
        return new GroupBySQLNativeSegmentImpl(expressionContext, sqlSegment, sqlNativeExpression);
    }

    @Override
    public SubQueryColumnSegment createSubQueryColumnSegment(TableAvailable table, Query<?> subQuery, String alias, QueryRuntimeContext runtimeContext) {
        return new SubQueryColumnSegmentImpl(table, subQuery, alias, runtimeContext);
    }

    @Override
    public CloneableSQLSegment createSQLColumnAsSegment(CloneableSQLSegment sqlColumnSegment, String alias, QueryRuntimeContext runtimeContext) {
        return new SQLColumnAsSegmentImpl(sqlColumnSegment, alias, runtimeContext);
    }
}
