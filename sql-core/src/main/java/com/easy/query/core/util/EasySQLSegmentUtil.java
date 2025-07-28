package com.easy.query.core.util;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.MaybeAggregateColumnSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.PredicateUnit;
import com.easy.query.core.expression.segment.condition.predicate.PredicateUnitItem;
import com.easy.query.core.expression.segment.condition.predicate.PredicateUnitResult;
import com.easy.query.core.expression.segment.core.TableSQLSegment;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.visitor.TableVisitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static Map<String, PredicateUnitItem> findCommonPredicateUnits(List<List<PredicateUnit>> segmentLists) {
        if (segmentLists == null || segmentLists.size() < 2) {
            return Collections.emptyMap();
        }

        //找出所有 List 中共同的 key
        Set<String> commonKeys = segmentLists.get(0).stream()
                .map(o->o.key)
                .collect(Collectors.toSet());

        for (int i = 1; i < segmentLists.size(); i++) {
            Set<String> currentKeys = EasyCollectionUtil.toSetBy(segmentLists.get(i), s -> s.key);
            commonKeys.retainAll(currentKeys);
            if (commonKeys.isEmpty()) {
                return Collections.emptyMap(); // 没有交集，直接返回
            }
        }

        //提取第一个 list 中 key 匹配的 PredicateUnit 用作返回值（只保留一个副本）
        Map<String, PredicateUnitItem> predicateUnitMap = new LinkedHashMap<>();
        for (List<PredicateUnit> segmentList : segmentLists) {
            for (PredicateUnit predicateUnit : segmentList) {
                if (commonKeys.contains(predicateUnit.key)) {
                    PredicateUnitItem predicateUnitItem = predicateUnitMap.computeIfAbsent(predicateUnit.key, k -> new PredicateUnitItem(predicateUnit));
                    predicateUnitItem.addPredicateUnit(predicateUnit);
                }
            }
        }
        return predicateUnitMap;
    }
}
