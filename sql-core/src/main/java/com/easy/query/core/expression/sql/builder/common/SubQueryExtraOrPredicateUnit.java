package com.easy.query.core.expression.sql.builder.common;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2025/8/24 08:34
 * 文件说明
 *
 * @author xuejiaming
 */
public class SubQueryExtraOrPredicateUnit extends AbstractSubQueryExtraPredicateUnit {
    private final AndPredicateSegment andPredicateSegment;
    private final List<SQLActionExpression1<WherePredicate<Object>>> whereActionList;
    protected boolean isInvoke;

    public SubQueryExtraOrPredicateUnit(AndPredicateSegment andPredicateSegment, TableAvailable fromTable, String[] selfProperties, String[] targetProperties, WherePredicate<Object> wherePredicate) {
        super(fromTable, selfProperties, targetProperties, wherePredicate);
        this.andPredicateSegment = andPredicateSegment;
        this.whereActionList = new ArrayList<>();
        this.isInvoke = true;
        parsePredicate();
    }

    private void parsePredicate() {
        List<PredicateSegment> children = andPredicateSegment.getChildren();
        for (PredicateSegment predicateSegment : children) {
            if (this.isInvoke) {
                List<Predicate> flatAndPredicates = predicateSegment.getFlatAndPredicates();
                if (EasyCollectionUtil.isSingle(flatAndPredicates)) {
                    Predicate first = EasyCollectionUtil.first(flatAndPredicates);

                    SQLActionExpression1<WherePredicate<Object>> whereAction = parsePredicate(first);
                    if (whereAction != null) {
                        this.whereActionList.add(whereAction);
                    } else {
                        this.isInvoke = false;
                    }
                } else {
                    this.isInvoke = false;
                }
            }
        }
    }

    @Override
    public void invoke() {
        if (this.isInvoke) {
            if (EasyCollectionUtil.isNotEmpty(whereActionList)) {
                wherePredicate.and(() -> {
                    for (SQLActionExpression1<WherePredicate<Object>> predicateSQLAction : whereActionList) {
                        predicateSQLAction.apply(wherePredicate);
                        wherePredicate.or();
                    }
                });
            }
        }
    }
}
