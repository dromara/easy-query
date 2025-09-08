package com.easy.query.core.expression.predicate;

import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/9/8 16:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class SmartPredicateOrUnit extends AbstractSmartPredicateUnit{
    private final AndPredicateSegment andPredicateSegment;
    private final List<SQLActionExpression1<Filter>> whereActionList;
    protected boolean isInvoke;


    public SmartPredicateOrUnit(AndPredicateSegment andPredicateSegment, TableAvailable table, Map<String, SmartPredicateItem> aliasMap) {
        super(table, aliasMap);
        this.andPredicateSegment = andPredicateSegment;
        this.whereActionList = new ArrayList<>();
        this.isInvoke = true;
        parsePredicate();
    }

    private void parsePredicate() {
        List<PredicateSegment> children = andPredicateSegment.getChildren();
        for (PredicateSegment predicateSegment : children) {
            if (this.isInvoke) {//or里面必须所有的都是这个条件才可以
                List<Predicate> flatAndPredicates = predicateSegment.getFlatAndPredicates();
                if (EasyCollectionUtil.isSingle(flatAndPredicates)) {
                    Predicate first = EasyCollectionUtil.first(flatAndPredicates);

                    SQLActionExpression1<Filter> whereAction = parsePredicate(first);
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
    public void invoke(Filter filter) {
        if (this.isInvoke) {
            if (EasyCollectionUtil.isNotEmpty(whereActionList)) {
                filter.and(f -> {
                    for (SQLActionExpression1<Filter> predicateSQLAction : whereActionList) {
                        predicateSQLAction.apply(f);
                        f.or();
                    }
                });
            }
        }
    }
}
