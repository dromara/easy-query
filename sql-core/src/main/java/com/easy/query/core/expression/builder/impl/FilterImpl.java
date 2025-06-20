package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.ColumnValue2Segment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.OrPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnCollectionPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnExistsSubQueryPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnInSubQueryPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnMultiCollectionPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnNoneSubQueryPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnNullAssertPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnRelationCollectionPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnTrueOrFalsePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnWithColumnPredicate;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.segment.condition.predicate.SQLNativePredicateImpl;
import com.easy.query.core.expression.segment.condition.predicate.SQLNativesPredicateImpl;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyColumnSegmentUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * create time 2023/6/22 14:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class FilterImpl implements Filter {
    private final QueryRuntimeContext runtimeContext;
    private final ExpressionContext expressionContext;
    private final PredicateSegment rootPredicateSegment;
    private final boolean reverse;
    private final ValueFilter conditionAcceptAssert;
    private PredicateSegment nextPredicateSegment;

    public FilterImpl(QueryRuntimeContext runtimeContext, ExpressionContext expressionContext, PredicateSegment predicateSegment, boolean reverse, ValueFilter conditionAcceptAssert) {


        this.runtimeContext = runtimeContext;
        this.expressionContext = expressionContext;
        this.rootPredicateSegment = predicateSegment;
        this.reverse = reverse;
        this.conditionAcceptAssert = conditionAcceptAssert;
        this.nextPredicateSegment = new AndPredicateSegment();
    }

    @Override
    public boolean isOr() {
        return nextPredicateSegment instanceof OrPredicateSegment;
    }

    protected void nextAnd() {
        if (nextPredicateSegment.isNotEmpty()) {
            this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        }
        this.nextPredicateSegment = new AndPredicateSegment();
    }

    protected void nextOr() {
        if (nextPredicateSegment.isNotEmpty()) {
            this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        }
        this.nextPredicateSegment = new OrPredicateSegment();
    }

    public void next() {
        if (reverse) {
            nextOr();
        } else {
            nextAnd();
        }
    }

//    protected void _next(boolean nextOr) {
//        if (reverse) {
//            if (nextOr) {
//                nextAnd();
//            } else {
//                nextOr();
//            }
//        } else {
//            if (nextOr) {
//                nextOr();
//            } else {
//                nextAnd();
//            }
//        }
//    }

    protected SQLPredicateCompare getReallyPredicateCompare(SQLPredicateCompare sqlPredicateCompare) {
        return reverse ? sqlPredicateCompare.toReverse() : sqlPredicateCompare;
    }

    public void appendThisPredicate(TableAvailable table, String property, Object val, SQLPredicateCompare condition) {
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
        Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, expressionContext);
        SQLPredicateCompare reallyPredicateCompare = getReallyPredicateCompare(condition);
        ColumnValue2Segment compareValue2Segment = EasyColumnSegmentUtil.createColumnCompareValue2Segment(table, columnMetadata, expressionContext, val, reallyPredicateCompare.isLike());

        nextPredicateSegment.setPredicate(new ColumnValuePredicate(column2Segment, compareValue2Segment, reallyPredicateCompare));
//        Predicate columnPredicate = EasySQLExpressionUtil.getSQLOwnerPredicateSegmentColumnMetadata(expressionContext, table, columnMetadata, getReallyPredicateCompare(condition), val);
//        nextPredicateSegment.setPredicate(columnPredicate);
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }

    @Override
    public ValueFilter getValueFilter() {
        return this.conditionAcceptAssert;
    }

    @Override
    public PredicateSegment getRootPredicateSegment() {
        return rootPredicateSegment;
    }

    @Override
    public boolean getReverse() {
        return reverse;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public Filter gt(TableAvailable table, String property, Object val) {
        if (conditionAppend(table, property, val)) {
            appendThisPredicate(table, property, val, SQLPredicateCompareEnum.GT);
            next();
        }
        return this;
    }

    @Override
    public Filter ge(TableAvailable table, String property, Object val) {
        if (conditionAppend(table, property, val)) {
            appendThisPredicate(table, property, val, SQLPredicateCompareEnum.GE);
            next();
        }
        return this;
    }

    @Override
    public Filter eq(TableAvailable table, String property, Object val) {
        if (conditionAppend(table, property, val)) {
            appendThisPredicate(table, property, val, SQLPredicateCompareEnum.EQ);
            next();
        }
        return this;
    }

    @Override
    public Filter ne(TableAvailable table, String property, Object val) {
        if (conditionAppend(table, property, val)) {
            appendThisPredicate(table, property, val, SQLPredicateCompareEnum.NE);
            next();
        }
        return this;
    }

    @Override
    public Filter le(TableAvailable table, String property, Object val) {
        if (conditionAppend(table, property, val)) {
            appendThisPredicate(table, property, val, SQLPredicateCompareEnum.LE);
            next();
        }
        return this;
    }

    @Override
    public Filter lt(TableAvailable table, String property, Object val) {
        if (conditionAppend(table, property, val)) {
            appendThisPredicate(table, property, val, SQLPredicateCompareEnum.LT);
            next();
        }
        return this;
    }

    @Override
    public Filter compare(TableAvailable table, String property, Object val, SQLPredicateCompareEnum sqlPredicateCompare) {
        if (conditionAppend(table, property, val)) {
            appendThisPredicate(table, property, val, sqlPredicateCompare);
            next();
        }
        return this;
    }

    @Override
    public Filter like(TableAvailable table, String property, Object val, SQLLikeEnum sqlLike) {

        if (conditionAppend(table, property, val)) {
            appendThisPredicate(table, property, EasySQLUtil.getLikeParameter(val, sqlLike), SQLPredicateCompareEnum.LIKE);
            next();
        }
        return this;
    }

    @Override
    public Filter likeRaw(TableAvailable table, String property, Object val, SQLLikeEnum sqlLike) {
        if (conditionAppend(table, property, val)) {
            appendThisPredicate(table, property, EasySQLUtil.getLikeRawParameter(val, sqlLike), SQLPredicateCompareEnum.LIKE);
            next();
        }
        return this;
    }

    @Override
    public Filter notLikeRaw(TableAvailable table, String property, Object val, SQLLikeEnum sqlLike) {
        if (conditionAppend(table, property, val)) {
            appendThisPredicate(table, property, EasySQLUtil.getLikeRawParameter(val, sqlLike), SQLPredicateCompareEnum.NOT_LIKE);
            next();
        }
        return this;
    }

    @Override
    public Filter notLike(TableAvailable table, String property, Object val, SQLLikeEnum sqlLike) {
        if (conditionAppend(table, property, val)) {
            appendThisPredicate(table, property, EasySQLUtil.getLikeParameter(val, sqlLike), SQLPredicateCompareEnum.NOT_LIKE);
            next();
        }
        return this;
    }

    @Override
    public Filter isNull(TableAvailable table, String property) {
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
        Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, expressionContext);
        nextPredicateSegment.setPredicate(new ColumnNullAssertPredicate(column2Segment, getReallyPredicateCompare(SQLPredicateCompareEnum.IS_NULL)));
        next();
        return this;
    }

    @Override
    public Filter isNotNull(TableAvailable table, String property) {
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
        Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, expressionContext);
        nextPredicateSegment.setPredicate(new ColumnNullAssertPredicate(column2Segment, getReallyPredicateCompare(SQLPredicateCompareEnum.IS_NOT_NULL)));
        next();
        return this;
    }

    @Override
    public Filter isNull(TableAvailable table, SQLFunction sqlFunction) {
        return assertSQLFunction(table, sqlFunction, SQLPredicateCompareEnum.IS_NULL);
    }

    @Override
    public Filter isNotNull(TableAvailable table, SQLFunction sqlFunction) {
        return assertSQLFunction(table, sqlFunction, SQLPredicateCompareEnum.IS_NOT_NULL);
    }

    private Filter assertSQLFunction(TableAvailable table, SQLFunction sqlFunction, SQLPredicateCompare sqlPredicateAssert) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);


//        SQLSegment sqlSegment = new SQLFunctionTranslateImpl(sqlFunction)
//                .toSQLSegment(sqlNativeExpressionContext, table, null,null);
//
//        SQLNativePredicate2Impl sqlNativePredicate2 = new SQLNativePredicate2Impl(expressionContext, sqlSegment, s -> s + " " + sqlPredicateAssert.getSQL(), sqlNativeExpressionContext);
//        nextPredicateSegment.setPredicate(sqlNativePredicate2);
//

        String sqlSegment = sqlFunction.sqlSegment(table);
        sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table, sqlNativeExpressionContext));
        nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(expressionContext, sqlSegment + " " + sqlPredicateAssert.getSQL(), sqlNativeExpressionContext));

        next();
        return this;
    }

    @Override
    public Filter in(TableAvailable table, String property, Collection<?> collection) {
        if (conditionAppend(table, property, collection)) {
            ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
            Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, expressionContext);
//            List<ColumnValue2Segment> columnValue2Segments = collection.stream().map(o -> EasyColumnSegmentUtil.createColumnCompareValue2Segment(table, columnMetadata, expressionContext, o)).collect(Collectors.toList());
            List<ColumnValue2Segment> columnValue2Segments = EasyCollectionUtil.select(collection, (o, i) -> EasyColumnSegmentUtil.createColumnCompareValue2Segment(table, columnMetadata, expressionContext, o));

            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(column2Segment, columnValue2Segments, getReallyPredicateCompare(SQLPredicateCompareEnum.IN), expressionContext));
            next();
        }
        return this;
    }

    @Override
    public Filter relationIn(TableAvailable table, String[] properties, List<List<Object>> relationIds) {
        nextPredicateSegment.setPredicate(new ColumnRelationCollectionPredicate(table, properties, relationIds, getReallyPredicateCompare(SQLPredicateCompareEnum.IN), expressionContext));
        next();
        return this;
    }

    @Override
    public Filter relationEq(TableAvailable table, String[] properties, List<List<Object>> relationId) {
        List<Column2Segment> column2Segments = Arrays.stream(properties).map(o -> {
            ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(o);
            return EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, expressionContext);
        }).collect(Collectors.toList());

        List<List<ColumnValue2Segment>> columnValue2Segments = EasyCollectionUtil.select(relationId, (innerCollections, i) -> {
            return EasyCollectionUtil.select(innerCollections, (o, ii) -> EasyColumnSegmentUtil.createColumnCompareValue2Segment(table, column2Segments.get(ii).getColumnMetadata(), expressionContext, o));
        });
        nextPredicateSegment.setPredicate(new ColumnMultiCollectionPredicate(table, column2Segments, columnValue2Segments, getReallyPredicateCompare(SQLPredicateCompareEnum.IN), expressionContext));
        next();
        return this;
    }

    @Override
    public <TProperty> Filter in(TableAvailable table, String property, TProperty[] collection) {
        if (conditionAppend(table, property, collection)) {
            ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
            Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, expressionContext);
//            List<ColumnValue2Segment> columnValue2Segments = Arrays.stream(collection).map(o -> EasyColumnSegmentUtil.createColumnCompareValue2Segment(table, columnMetadata, expressionContext, o)).collect(Collectors.toList());
            List<ColumnValue2Segment> columnValue2Segments = EasyCollectionUtil.select(collection, (o, i) -> EasyColumnSegmentUtil.createColumnCompareValue2Segment(table, columnMetadata, expressionContext, o));

            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(column2Segment, columnValue2Segments, getReallyPredicateCompare(SQLPredicateCompareEnum.IN), expressionContext));
            next();
        }
        return this;
    }

    private <T2> void extract(Query<T2> subQuery) {
        EntityQueryExpressionBuilder subQueryableSQLEntityExpressionBuilder = subQuery.getSQLEntityExpressionBuilder();
        expressionContext.extract(subQueryableSQLEntityExpressionBuilder.getExpressionContext());
    }

    private <TProperty> void subQueryFilter0(TableAvailable table, String property, Query<TProperty> subQueryable, SQLPredicateCompare sqlPredicateCompare) {
        Query<TProperty> tPropertyQuery = subQueryable.cloneQueryable();
        extract(tPropertyQuery);
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
        Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, expressionContext);
        nextPredicateSegment.setPredicate(new ColumnInSubQueryPredicate(column2Segment, tPropertyQuery, getReallyPredicateCompare(sqlPredicateCompare), expressionContext));
        next();
    }

    private void funcValueFilter0(TableAvailable table, SQLFunction sqlFunction, Object val, SQLPredicateCompare sqlPredicateCompare) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        SQLPredicateCompare predicateCompare = getReallyPredicateCompare(sqlPredicateCompare);

//
        String sqlSegment = sqlFunction.sqlSegment(table);
        sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table, sqlNativeExpressionContext));
        sqlNativeExpressionContext.value(val);
        nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(expressionContext, sqlSegment + " " + predicateCompare.getSQL() + " {" + sqlFunction.paramMarks() + "}", sqlNativeExpressionContext));
        next();
    }

    private void funcValueBetweenFilter0(TableAvailable table, SQLFunction sqlFunction, Object left, Object right) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);

//
        String sqlSegment = sqlFunction.sqlSegment(table);
        sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table, sqlNativeExpressionContext));
        sqlNativeExpressionContext.value(left);
        sqlNativeExpressionContext.value(right);
        nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(expressionContext, sqlSegment + " BETWEEN {" + sqlFunction.paramMarks() + "} AND {" + (sqlFunction.paramMarks()+1) + "}", sqlNativeExpressionContext));
        next();
    }

    private void funcColumnFilter0(TableAvailable table1, SQLFunction sqlFunction, TableAvailable table2, String property, SQLPredicateCompare sqlPredicateCompare) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        SQLPredicateCompare predicateCompare = getReallyPredicateCompare(sqlPredicateCompare);

//        SQLSegment sqlSegment = new SQLFunctionTranslateImpl(sqlFunction)
//                .toSQLSegment(sqlNativeExpressionContext, table1, null,()->sqlNativeExpressionContext.expression(table2, property));
//
//        SQLNativePredicate2Impl sqlNativePredicate2 = new SQLNativePredicate2Impl(expressionContext, sqlSegment, s -> s + " " + predicateCompare.getSQL() + " {" + sqlFunction.paramMarks() + "}", sqlNativeExpressionContext);
//        nextPredicateSegment.setPredicate(sqlNativePredicate2);

        String sqlSegment = sqlFunction.sqlSegment(table1);
        sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table1, sqlNativeExpressionContext));
        sqlNativeExpressionContext.expression(table2, property);
        nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(expressionContext, sqlSegment + " " + predicateCompare.getSQL() + " {" + sqlFunction.paramMarks() + "}", sqlNativeExpressionContext));

        next();
    }

    private <TProperty> void funcSubQueryFilter0(TableAvailable table1, SQLFunction sqlFunction, Query<TProperty> subQuery, SQLPredicateCompare sqlPredicateCompare) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        SQLPredicateCompare predicateCompare = getReallyPredicateCompare(sqlPredicateCompare);
//        Query<TProperty> tPropertyQuery = subQuery.cloneQueryable();
//        extract(tPropertyQuery);
//        SQLSegment sqlSegment = new SQLFunctionTranslateImpl(sqlFunction)
//                .toSQLSegment(sqlNativeExpressionContext, table1, null,()->sqlNativeExpressionContext.expression(tPropertyQuery,false));
//
//        SQLNativePredicate2Impl sqlNativePredicate2 = new SQLNativePredicate2Impl(expressionContext, sqlSegment, s -> s + " " + predicateCompare.getSQL() + " {" + sqlFunction.paramMarks() + "}", sqlNativeExpressionContext);
//        nextPredicateSegment.setPredicate(sqlNativePredicate2);


        String sqlSegment = sqlFunction.sqlSegment(table1);
        sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table1, sqlNativeExpressionContext));
        sqlNativeExpressionContext.expression(subQuery.cloneQueryable());
        nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(expressionContext, sqlSegment + " " + predicateCompare.getSQL() + " {" + sqlFunction.paramMarks() + "}", sqlNativeExpressionContext));

        next();
    }

    private <TProperty> void funcInFilter0(TableAvailable table, SQLFunction sqlFunction, Collection<TProperty> collections, SQLPredicateCompare sqlPredicateCompare) {
        SQLPredicateCompare predicateCompare = getReallyPredicateCompare(sqlPredicateCompare);
        if (EasyCollectionUtil.isEmpty(collections)) {
            if (SQLPredicateCompareEnum.IN == predicateCompare) {
                nextPredicateSegment.setPredicate(new ColumnTrueOrFalsePredicate(false, sqlPredicateCompare, table));
                next();
                return;
            } else if (SQLPredicateCompareEnum.NOT_IN == predicateCompare) {
                nextPredicateSegment.setPredicate(new ColumnTrueOrFalsePredicate(true, sqlPredicateCompare, table));
                next();
                return;
            } else {
                throw new UnsupportedOperationException();
            }
        }
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);


//        SQLSegment sqlSegment = new SQLFunctionTranslateImpl(sqlFunction)
//                .toSQLSegment(sqlNativeExpressionContext, table, null,()->sqlNativeExpressionContext.collection(collections));
//
//        SQLNativePredicate2Impl sqlNativePredicate2 = new SQLNativePredicate2Impl(expressionContext, sqlSegment, s -> s + " " + predicateCompare.getSQL() + " ({" + sqlFunction.paramMarks() + "})", sqlNativeExpressionContext);
//        nextPredicateSegment.setPredicate(sqlNativePredicate2);


        String sqlSegment = sqlFunction.sqlSegment(table);
        sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table, sqlNativeExpressionContext));
        sqlNativeExpressionContext.collection(collections);
        nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(expressionContext, sqlSegment + " " + predicateCompare.getSQL() + " ({" + sqlFunction.paramMarks() + "})", sqlNativeExpressionContext));

        next();
    }

    private void valueFuncFilter0(TableAvailable table, String property, TableAvailable tableRight, SQLFunction sqlFunction, SQLPredicateCompare sqlPredicateCompare) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);

        SQLPredicateCompare predicateCompare = getReallyPredicateCompare(sqlPredicateCompare);


//        SQLSegment sqlSegment = new SQLFunctionTranslateImpl(sqlFunction)
//                .toSQLSegment(sqlNativeExpressionContext, tableRight, null,()->sqlNativeExpressionContext.expression(table, property));
//
//
//        SQLNativePredicate2Impl sqlNativePredicate2 = new SQLNativePredicate2Impl(expressionContext, sqlSegment, s ->  " {" + sqlFunction.paramMarks() + "} " + predicateCompare.getSQL()+ " " + s, sqlNativeExpressionContext);
//        nextPredicateSegment.setPredicate(sqlNativePredicate2);

        String sqlSegment = sqlFunction.sqlSegment(tableRight);
        sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table, sqlNativeExpressionContext));
        sqlNativeExpressionContext.expression(table, property);
        nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(expressionContext, " {" + sqlFunction.paramMarks() + "} " + predicateCompare.getSQL() + " " + sqlSegment, sqlNativeExpressionContext));

        next();
    }


    private void funcColumnFuncFilter0(TableAvailable tableLeft, SQLFunction sqlFunctionLeft, TableAvailable tableRight, SQLFunction sqlFunctionRight, SQLPredicateCompare sqlPredicateCompare) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContextLeft = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        sqlFunctionLeft.consume(new SQLNativeChainExpressionContextImpl(tableLeft, sqlNativeExpressionContextLeft));
        SQLNativeExpressionContextImpl sqlNativeExpressionContextRight = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        sqlFunctionRight.consume(new SQLNativeChainExpressionContextImpl(tableRight, sqlNativeExpressionContextRight));

        SQLPredicateCompare predicateCompare = getReallyPredicateCompare(sqlPredicateCompare);

        Predicate sqlNativePredicateLeft = EasySQLExpressionUtil.getSQLFunctionPredicate(expressionContext, tableLeft, sqlFunctionLeft, sqlNativeExpressionContextLeft);
        Predicate sqlNativePredicateRight = EasySQLExpressionUtil.getSQLFunctionPredicate(expressionContext, tableRight, sqlFunctionRight, sqlNativeExpressionContextRight);

        nextPredicateSegment.setPredicate(new SQLNativesPredicateImpl(runtimeContext, sqlNativePredicateLeft, predicateCompare, sqlNativePredicateRight));

        next();
    }

    private <T2> void subQueryExists(Query<T2> subQuery, SQLPredicateCompareEnum sqlPredicateCompare) {

        extract(subQuery);
        Query<T2> existsQuery = subQuery.cloneQueryable().select("1");

        nextPredicateSegment.setPredicate(new ColumnExistsSubQueryPredicate(existsQuery, getReallyPredicateCompare(sqlPredicateCompare), runtimeContext));
        next();
    }

    private <T2> void subQueryNone(Query<T2> subQuery) {

        extract(subQuery);
        Query<T2> noneQuery = subQuery.cloneQueryable().select("1");

        nextPredicateSegment.setPredicate(new ColumnNoneSubQueryPredicate(noneQuery, runtimeContext));
        next();
    }

    @Override
    public <TProperty> Filter in(TableAvailable table, String property, Query<TProperty> subQuery) {
        if (conditionAppend(table, property, subQuery)) {
            subQueryFilter0(table, property, subQuery, SQLPredicateCompareEnum.IN);
        }
        return this;
    }

    @Override
    public Filter notIn(TableAvailable table, String property, Collection<?> collection) {
        if (conditionAppend(table, property, collection)) {
            ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
            Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, expressionContext);
//            List<ColumnValue2Segment> columnValue2Segments = collection.stream().map(o -> EasyColumnSegmentUtil.createColumnCompareValue2Segment(table, columnMetadata, expressionContext, o)).collect(Collectors.toList());
            List<ColumnValue2Segment> columnValue2Segments = EasyCollectionUtil.select(collection, (o, i) -> EasyColumnSegmentUtil.createColumnCompareValue2Segment(table, columnMetadata, expressionContext, o));

            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(column2Segment, columnValue2Segments, getReallyPredicateCompare(SQLPredicateCompareEnum.NOT_IN), expressionContext));
            next();
        }
        return this;
    }

    @Override
    public <TProperty> Filter notIn(TableAvailable table, String property, TProperty[] collection) {
        if (conditionAppend(table, property, collection)) {
            ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
            Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, expressionContext);
//            List<ColumnValue2Segment> columnValue2Segments = Arrays.stream(collection).map(o -> EasyColumnSegmentUtil.createColumnCompareValue2Segment(table, columnMetadata, expressionContext, o)).collect(Collectors.toList());
            List<ColumnValue2Segment> columnValue2Segments = EasyCollectionUtil.select(collection, (o, i) -> EasyColumnSegmentUtil.createColumnCompareValue2Segment(table, columnMetadata, expressionContext, o));

            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(column2Segment, columnValue2Segments, getReallyPredicateCompare(SQLPredicateCompareEnum.NOT_IN), expressionContext));
            next();
        }
        return this;
    }

    @Override
    public <TProperty> Filter notIn(TableAvailable table, String property, Query<TProperty> subQuery) {
        if (conditionAppend(table, property, subQuery)) {
            subQueryFilter0(table, property, subQuery, SQLPredicateCompareEnum.NOT_IN);
        }
        return this;
    }

    @Override
    public <T2> Filter exists(Query<T2> subQuery) {
        if (conditionAppend(null, null, subQuery)) {
            subQueryExists(subQuery, SQLPredicateCompareEnum.EXISTS);
        }
        return this;
    }

    @Override
    public <T2> Filter notExists(Query<T2> subQuery) {
        if (conditionAppend(null, null, subQuery)) {
            subQueryExists(subQuery, SQLPredicateCompareEnum.NOT_EXISTS);
        }
        return this;
    }

    @Override
    public <T2> Filter none(Query<T2> subQuery) {
        if (conditionAppend(null, null, subQuery)) {
            subQueryNone(subQuery);
        }
        return this;
    }

//    @Override
//    public Filter range(TableAvailable table, String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight, SQLRangeEnum sqlRange) {
//
//        boolean acceptLeft = conditionLeft && conditionAppend(table, property, valLeft);
//        boolean acceptRight = conditionRight && conditionAppend(table, property, valRight);
//        if (acceptLeft && acceptRight) {
//            and(f -> {
//                boolean openFirst = SQLRangeEnum.openFirst(sqlRange);
//                f.appendThisPredicate(table, property, valLeft, getReallyPredicateCompare(openFirst ? SQLPredicateCompareEnum.GT : SQLPredicateCompareEnum.GE));
//                f.next();
//                boolean openEnd = SQLRangeEnum.openEnd(sqlRange);
//                f.appendThisPredicate(table, property, valRight, getReallyPredicateCompare(openEnd ? SQLPredicateCompareEnum.LT : SQLPredicateCompareEnum.LE));
//                f.next();
//            });
//        } else {
//            if (acceptLeft) {
//                boolean openFirst = SQLRangeEnum.openFirst(sqlRange);
//                appendThisPredicate(table, property, valLeft, getReallyPredicateCompare(openFirst ? SQLPredicateCompareEnum.GT : SQLPredicateCompareEnum.GE));
//                next();
//            }
//            if (acceptRight) {
//                boolean openEnd = SQLRangeEnum.openEnd(sqlRange);
//                appendThisPredicate(table, property, valRight, getReallyPredicateCompare(openEnd ? SQLPredicateCompareEnum.LT : SQLPredicateCompareEnum.LE));
//                next();
//            }
//        }
//        return this;
//    }

    @Override
    public Filter valueColumnFilter(TableAvailable leftTable, String property1, TableAvailable rightTable, String property2, SQLPredicateCompare sqlPredicateCompare) {
        ColumnMetadata leftColumnMetadata = leftTable.getEntityMetadata().getColumnNotNull(property1);
        Column2Segment leftColumn2Segment = EasyColumnSegmentUtil.createColumn2Segment(leftTable, leftColumnMetadata, expressionContext);

        ColumnMetadata rightColumnMetadata = rightTable.getEntityMetadata().getColumnNotNull(property2);
        Column2Segment rightColumn2Segment = EasyColumnSegmentUtil.createColumn2Segment(rightTable, rightColumnMetadata, expressionContext);
        nextPredicateSegment.setPredicate(new ColumnWithColumnPredicate(leftColumn2Segment, rightColumn2Segment, getReallyPredicateCompare(sqlPredicateCompare)));
        next();
        return this;
    }

    @Override
    public Filter like(TableAvailable leftTable, String property1, TableAvailable rightTable, String property2, boolean like, SQLLikeEnum sqlLike) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        SQLFunction likeSQLFunction = runtimeContext.fx().like(x -> {
            x.column(leftTable, property1)
                    .column(rightTable, property2);
        }, like, sqlLike);
        return getLikePredicateFilter(leftTable, sqlNativeExpressionContext, likeSQLFunction);
    }

    @Override
    public Filter like(TableAvailable leftTable, String property1, TableAvailable rightTable, SQLFunction sqlFunction, boolean like, SQLLikeEnum sqlLike) {

        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        SQLFunction likeSQLFunction = runtimeContext.fx().like(x -> {
            x.column(leftTable, property1)
                    .sqlFunc(rightTable, sqlFunction);
        }, like, sqlLike);
        return getLikePredicateFilter(leftTable, sqlNativeExpressionContext, likeSQLFunction);
    }

    @Override
    public Filter like(TableAvailable leftTable, SQLFunction sqlFunction, TableAvailable rightTable, String property2, boolean like, SQLLikeEnum sqlLike) {

        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        SQLFunction likeSQLFunction = runtimeContext.fx().like(x -> {
            x.sqlFunc(leftTable, sqlFunction)
                    .column(rightTable, property2);
        }, like, sqlLike);
        return getLikePredicateFilter(leftTable, sqlNativeExpressionContext, likeSQLFunction);
    }

    @Override
    public Filter like(TableAvailable leftTable, SQLFunction sqlFunction1, TableAvailable rightTable, SQLFunction sqlFunction2, boolean like, SQLLikeEnum sqlLike) {

        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        SQLFunction likeSQLFunction = runtimeContext.fx().like(x -> {
            x.sqlFunc(leftTable, sqlFunction1)
                    .sqlFunc(rightTable, sqlFunction2);
        }, like, sqlLike);
        return getLikePredicateFilter(leftTable, sqlNativeExpressionContext, likeSQLFunction);
    }

    private Filter getLikePredicateFilter(TableAvailable leftTable, SQLNativeExpressionContextImpl sqlNativeExpressionContext, SQLFunction likeSQLFunction) {


//        SQLSegment sqlSegment = new SQLFunctionTranslateImpl(likeSQLFunction)
//                .toSQLSegment(sqlNativeExpressionContext, leftTable, null,null);
//
//        SQLNativePredicate2Impl sqlNativePredicate2 = new SQLNativePredicate2Impl(expressionContext, sqlSegment, s -> s, sqlNativeExpressionContext);
//        nextPredicateSegment.setPredicate(sqlNativePredicate2);


        String sqlSegment = likeSQLFunction.sqlSegment(leftTable);
        likeSQLFunction.consume(new SQLNativeChainExpressionContextImpl(leftTable, sqlNativeExpressionContext));
        nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(expressionContext, sqlSegment, sqlNativeExpressionContext));

        next();
        return this;
    }

    @Override
    public <TProperty> Filter subQueryFilter(TableAvailable table, String property, Query<TProperty> subQuery, SQLPredicateCompare sqlPredicateCompare) {
        if (conditionAppend(table, property, subQuery)) {
            subQueryFilter0(table, property, subQuery, sqlPredicateCompare);
        }
        return this;
    }

    @Override
    public Filter funcValueFilter(TableAvailable table, SQLFunction sqlFunction, Object val, SQLPredicateCompare sqlPredicateCompare) {
        if (conditionAppend(table, null, val)) {
            funcValueFilter0(table, sqlFunction, val, sqlPredicateCompare);
        }
        return this;
    }

    @Override
    public Filter funcValueBetweenFilter(TableAvailable table, SQLFunction sqlFunction, Object left, Object right) {
        if (conditionAppend(table, null, Arrays.asList(left, right))) {
            funcValueBetweenFilter0(table, sqlFunction, left, right);
        }
        return this;
    }

    @Override
    public Filter funcColumnFilter(TableAvailable table1, SQLFunction sqlFunction, TableAvailable table2, String property, SQLPredicateCompare sqlPredicateCompare) {
        funcColumnFilter0(table1, sqlFunction, table2, property, sqlPredicateCompare);
        return this;
    }

    @Override
    public <TProperty> Filter funcSubQueryFilter(TableAvailable table, SQLFunction sqlFunction, Query<TProperty> subQuery, SQLPredicateCompare sqlPredicateCompare) {
        if (conditionAppend(table, null, subQuery)) {
            funcSubQueryFilter0(table, sqlFunction, subQuery, sqlPredicateCompare);
        }
        return this;
    }

    @Override
    public <TProperty> Filter funcInFilter(TableAvailable table, SQLFunction sqlFunction, Collection<TProperty> collections, SQLPredicateCompare sqlPredicateCompare) {
        if (conditionAppend(table, null, collections)) {
            funcInFilter0(table, sqlFunction, collections, sqlPredicateCompare);
        }
        return this;
    }

    @Override
    public Filter valueFuncFilter(TableAvailable table, String property, TableAvailable tableRight, SQLFunction sqlFunction, SQLPredicateCompare sqlPredicateCompare) {
        if (conditionAppend(table, property, sqlFunction)) {
            valueFuncFilter0(table, property, tableRight, sqlFunction, sqlPredicateCompare);
        }
        return this;
    }

    @Override
    public Filter funcColumnFuncFilter(TableAvailable tableLeft, SQLFunction sqlFunctionLeft, TableAvailable tableRight, SQLFunction sqlFunctionRight, SQLPredicateCompare sqlPredicateCompare) {
        if (conditionAppend(tableLeft, null, sqlFunctionRight)) {
            funcColumnFuncFilter0(tableLeft, sqlFunctionLeft, tableRight, sqlFunctionRight, sqlPredicateCompare);
        }
        return this;
    }

    @Override
    public Filter sqlNativeSegment(String sqlSegment, SQLActionExpression1<SQLNativeExpressionContext> contextConsume) {
        Objects.requireNonNull(contextConsume, "sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        contextConsume.apply(sqlNativeExpressionContext);
        nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(expressionContext, sqlSegment, sqlNativeExpressionContext));
        next();
        return this;
    }

    protected void and0() {
        if (reverse) {
            this.nextPredicateSegment = new OrPredicateSegment();
        } else {
            this.nextPredicateSegment = new AndPredicateSegment();
        }
    }

    @Override
    public Filter and() {
        and0();
        return this;
    }

    @Override
    public Filter and(SQLActionExpression1<Filter> sqlWherePredicateSQLExpression) {
        and0();
        Filter filter = create();
        sqlWherePredicateSQLExpression.apply(filter);
        next();
        return this;
    }
//    @Override
//    public Filter _and(SQLExpression1<Filter> sqlWherePredicateSQLExpression,boolean nextOr) {
//        and0();
//        Filter filter = create();
//        sqlWherePredicateSQLExpression.apply(filter);
//        _next(nextOr);
//        return this;
//    }

    protected void or0() {
        if (reverse) {
            this.nextPredicateSegment = new AndPredicateSegment();
        } else {
            this.nextPredicateSegment = new OrPredicateSegment();
        }
    }

    @Override
    public Filter or() {
        or0();
        return this;
    }

    @Override
    public Filter or(SQLActionExpression1<Filter> sqlWherePredicateSQLExpression) {
        or0();
        Filter filter = create();
        sqlWherePredicateSQLExpression.apply(filter);
        next();
        return this;
    }

//    @Override
//    public Filter _or(SQLExpression1<Filter> sqlWherePredicateSQLExpression, boolean nextOr) {
//        or0();
//        Filter filter = create();
//        sqlWherePredicateSQLExpression.apply(filter);
//        _next(nextOr);
//        return this;
//    }

    @Override
    public Filter create() {
        return new FilterImpl(runtimeContext, expressionContext, this.nextPredicateSegment, reverse, this.conditionAcceptAssert);
    }

    @Override
    public void valueCompare(TableAvailable table, String property, Object val, SQLPredicateCompare condition) {
        appendThisPredicate(table, property, val, condition);
        next();
    }

}
