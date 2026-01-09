package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLKeywordEnum;
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
import java.util.List;

/**
 * @author xuejiaming
 * @FileName: ColumnCollectionPredicate.java
 * @Description: 表达式集合判断
 * create time 2023/2/14 23:34
 */
public class ColumnMultiCollectionPredicate implements ValuesPredicate, ShardingPredicate {
    private final List<Column2Segment> column2Segments;
    private final List<List<ColumnValue2Segment>> collections;
    private final SQLPredicateCompare compare;
    private final ExpressionContext expressionContext;
    private final TableAvailable table;

    public ColumnMultiCollectionPredicate(TableAvailable table, List<Column2Segment> column2Segments, List<List<ColumnValue2Segment>> collections, SQLPredicateCompare compare, ExpressionContext expressionContext) {
        this.table = table;
        this.column2Segments = column2Segments;
        this.collections = collections;
        this.compare = compare;
        this.expressionContext = expressionContext;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        if (EasyCollectionUtil.isEmpty(collections)) {
            if (SQLPredicateCompareEnum.IN == compare) {
                return "1 = 2";//FALSE  oracle等数据库不支持
            } else {
                throw new UnsupportedOperationException();
            }
        } else {
            StringBuilder sql = new StringBuilder();
            sql.append("(");
            int i = 0;
            for (List<ColumnValue2Segment> collection : collections) {
                if (i != 0) {
                    sql.append(" ").append(SQLKeywordEnum.OR.toSQL()).append(" ");
                }
                appendProperty(sql, toSQLContext, collection);
                i++;
            }
            sql.append(")");
            return sql.toString();
        }
    }

    private void appendProperty(StringBuilder sql, ToSQLContext toSQLContext, List<ColumnValue2Segment> vals) {
        sql.append("(");
        int i = 0;
        for (Column2Segment column2Segment : column2Segments) {
            String sqlColumnSegment = column2Segment.toSQL(toSQLContext);
            ColumnValue2Segment val = vals.get(i);
            if (i != 0) {
                sql.append(" ").append(SQLKeywordEnum.AND.toSQL()).append(" ");
            }
            sql.append(sqlColumnSegment).append(" ").append(SQLPredicateCompareEnum.EQ.getSQL())
                    .append(val.toSQL(toSQLContext));
            i++;
        }
        sql.append(")");
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
        return new ColumnMultiCollectionPredicate(table, column2Segments, collections, compare, expressionContext);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }

    @Override
    public Collection<SQLParameter> getParameters() {
        return new ArrayList<>();
    }

    public List<Column2Segment> getColumn2Segments() {
        return column2Segments;
    }

    public List<List<ColumnValue2Segment>> getCollections() {
        return collections;
    }

    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }
}
