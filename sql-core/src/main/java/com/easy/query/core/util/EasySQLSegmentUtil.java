package com.easy.query.core.util;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.MaybeAggregateColumnSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.core.TableSQLSegment;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.visitor.TableVisitor;

import java.util.Collection;

/**
 * create time 2023/4/23 13:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasySQLSegmentUtil {
    public static boolean isEmpty(PredicateSegment predicateSegment) {
        return predicateSegment == null || predicateSegment.isEmpty();
    }

    public static boolean isNotEmpty(PredicateSegment predicateSegment) {
        return !isEmpty(predicateSegment);
    }

    public static boolean isEmpty(SQLBuilderSegment sqlBuilderSegment) {
        return sqlBuilderSegment == null || sqlBuilderSegment.isEmpty();
    }

    public static boolean isNotEmpty(SQLBuilderSegment sqlBuilderSegment) {
        return !isEmpty(sqlBuilderSegment);
    }

    public static boolean isAggregateColumn(SQLSegment sqlSegment) {
        if (sqlSegment instanceof MaybeAggregateColumnSegment) {
            return ((MaybeAggregateColumnSegment) sqlSegment).isAggregateColumn();
        }
        return false;
    }

    public static boolean isColumnMetadataSQLSegment(SQLSegment sqlSegment) {
        if (sqlSegment instanceof ColumnSegment) {
            return ((ColumnSegment) sqlSegment).getColumnMetadata() != null;
        }
        return false;
    }


    public static boolean tableVisit(@Nullable PredicateSegment predicateSegment, TableVisitor tableVisitor) {
        if (isEmpty(predicateSegment)) {
            return false;
        }
        return predicateSegment.forEach(predicate -> {
            predicate.accept(tableVisitor);
            return false;
        });
    }

    public static boolean tableVisit(@Nullable SQLBuilderSegment sqlBuilderSegment, TableVisitor tableVisitor) {
        if (isEmpty(sqlBuilderSegment)) {
            return false;
        }
        return sqlBuilderSegment.forEach(sqlSegment -> sqlSegmentTableVisit(sqlSegment, tableVisitor));
    }

    public static boolean sqlSegmentTableVisit(@Nullable SQLSegment sqlSegment, TableVisitor tableVisitor) {
        if (sqlSegment instanceof TableSQLSegment) {
             ((TableSQLSegment) sqlSegment).accept(tableVisitor);
        }
        return false;
    }

    public static boolean paramExpressionTableVisit(Collection<ParamExpression> paramExpressions, TableVisitor tableVisitor) {

        for (ParamExpression expression : paramExpressions) {
            expression.accept(tableVisitor);
        }
        return false;
    }
}
