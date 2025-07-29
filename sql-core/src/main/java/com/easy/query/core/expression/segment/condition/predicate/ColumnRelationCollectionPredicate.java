package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.ColumnValue2Segment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyColumnSegmentUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author xuejiaming
 * @FileName: ColumnCollectionPredicate.java
 * @Description: 表达式集合判断
 * create time 2023/2/14 23:34
 */
public class ColumnRelationCollectionPredicate implements ValuesPredicate, ShardingPredicate {
    private final List<List<Object>> relationIds;
    private final SQLPredicateCompare compare;
    private final ExpressionContext expressionContext;
    private final TableAvailable table;
    private final String[] propertyNames;
    private final ValuesPredicate valuesPredicate;

    public ColumnRelationCollectionPredicate(TableAvailable table, String[] propertyNames, List<List<Object>> relationIds, SQLPredicateCompare compare, ExpressionContext expressionContext) {
        this.table = table;
        this.propertyNames = propertyNames;
        this.relationIds = relationIds;
        this.compare = compare;
        this.expressionContext = expressionContext;
        this.valuesPredicate = getValuesPredicate();
    }

    private Collection<Object> getSingleFromNestCollection(List<List<Object>> collection) {
        ArrayList<Object> result = new ArrayList<>(collection.size());
        for (List<Object> objects : collection) {
            if (objects == null) {
                throw new EasyQueryInvalidOperationException("nest collection has null element");
            }
            if (objects.size() != 1) {
                throw new EasyQueryInvalidOperationException("nest collection element.size() != 1");
            }
            result.add(EasyCollectionUtil.first(objects));
        }
        return result;
    }

    private ValuesPredicate getValuesPredicate() {

        if (propertyNames.length == 1) {
            Collection<Object> collection = getSingleFromNestCollection(relationIds);

            ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyNames[0]);
            Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, expressionContext);
            List<ColumnValue2Segment> columnValue2Segments = EasyCollectionUtil.select(collection, (o, i) -> EasyColumnSegmentUtil.createColumnCompareValue2Segment(table, columnMetadata, expressionContext, o));

            return new ColumnCollectionPredicate(column2Segment, columnValue2Segments, compare, expressionContext);
        } else {
            List<Column2Segment> column2Segments = Arrays.stream(propertyNames).map(o -> {
                ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(o);
                return EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, expressionContext);
            }).collect(Collectors.toList());

            List<List<ColumnValue2Segment>> columnValue2Segments = EasyCollectionUtil.select(relationIds, (innerCollections, i) -> {
                return EasyCollectionUtil.select(innerCollections, (o, ii) -> EasyColumnSegmentUtil.createColumnCompareValue2Segment(table, column2Segments.get(ii).getColumnMetadata(), expressionContext, o));
            });
            return new ColumnMultiCollectionPredicate(table, column2Segments, columnValue2Segments, compare, expressionContext);
        }
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return valuesPredicate.toSQL(toSQLContext);
    }


    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return null;
    }

    @Override
    public Predicate cloneSQLColumnSegment() {
        return new ColumnRelationCollectionPredicate(table, propertyNames, relationIds, compare, expressionContext);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }

    @Override
    public Collection<SQLParameter> getParameters() {
        return new ArrayList<>();
    }
}
