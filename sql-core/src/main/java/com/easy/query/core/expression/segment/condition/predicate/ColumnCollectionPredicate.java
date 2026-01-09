package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.ColumnValue2Segment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * @author xuejiaming
 * @FileName: ColumnCollectionPredicate.java
 * @Description: 表达式集合判断
 * create time 2023/2/14 23:34
 */
public class ColumnCollectionPredicate implements ValuesPredicate, ShardingPredicate {
    private final Collection<ColumnValue2Segment> collection;
    private final SQLPredicateCompare compare;
    private final ExpressionContext expressionContext;
    private final Column2Segment column2Segment;

    public ColumnCollectionPredicate(Column2Segment column2Segment, Collection<ColumnValue2Segment> collection, SQLPredicateCompare compare, ExpressionContext expressionContext) {
        this.column2Segment = column2Segment;
        this.collection = collection;
        this.compare = compare;
        this.expressionContext = expressionContext;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        if (EasyCollectionUtil.isEmpty(collection)) {
            if (SQLPredicateCompareEnum.IN == compare) {
                return "1 = 2";//FALSE  oracle等数据库不支持
            } else if (SQLPredicateCompareEnum.NOT_IN == compare) {
                return "1 = 1";//TRUE  oracle等数据库不支持
            } else {
                throw new UnsupportedOperationException();
            }
        } else {

            String sqlColumnSegment =column2Segment.toSQL(toSQLContext);
            String compareSQL = compare.getSQL();
            StringBuilder sql = new StringBuilder();
            sql.append(sqlColumnSegment).append(" ").append(compareSQL).append(" (");
            Iterator<ColumnValue2Segment> iterator = collection.iterator();
            ColumnValue2Segment firstColumnValue2Segment = iterator.next();
            sql.append(firstColumnValue2Segment.toSQL(toSQLContext));
            while (iterator.hasNext()) {
                ColumnValue2Segment columnValue2Segment = iterator.next();
                sql.append(",").append(columnValue2Segment.toSQL(toSQLContext));
            }
            sql.append(")");
            return sql.toString();
        }
    }

    @Override
    public TableAvailable getTable() {
        return column2Segment.getTable();
    }

    @Override
    public String getPropertyName() {
        return column2Segment.getColumnMetadata().getPropertyName();
    }

    @Override
    public Predicate cloneSQLColumnSegment() {

       return new ColumnCollectionPredicate(column2Segment,collection,compare,expressionContext);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }

    @Override
    public Collection<SQLParameter> getParameters() {
        if (EasyCollectionUtil.isEmpty(collection)) {
            return Collections.emptyList();
        }
        ArrayList<SQLParameter> sqlParameters = new ArrayList<>(collection.size());
        for (ColumnValue2Segment o : collection) {
            sqlParameters.add(o.getSQLParameter());
        }
        return sqlParameters;
    }

    public Collection<ColumnValue2Segment> getCollection() {
        return collection;
    }

    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }

    public Column2Segment getColumn2Segment() {
        return column2Segment;
    }
}
