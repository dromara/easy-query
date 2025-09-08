package com.easy.query.core.expression.predicate;

import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
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
public class SmartPredicateAndUnit extends AbstractSmartPredicateUnit{
    private final AndPredicateSegment andPredicateSegment;
    private final List<SQLActionExpression1<Filter>> whereActionList;


    public SmartPredicateAndUnit(AndPredicateSegment andPredicateSegment, TableAvailable table, Map<String, SmartPredicateItem> aliasMap) {
        super(table, aliasMap);
        this.andPredicateSegment = andPredicateSegment;
        this.whereActionList = new ArrayList<>();
        parsePredicate();
    }

    private void parsePredicate() {
        List<Predicate> flatAndPredicates = andPredicateSegment.getFlatAndPredicates();
        if (EasyCollectionUtil.isNotEmpty(flatAndPredicates)) {
            for (Predicate predicate : flatAndPredicates) {
                SQLActionExpression1<Filter> whereAction = parsePredicate(predicate);
                if (whereAction != null) {
                    this.whereActionList.add(whereAction);
                }
            }
        }
    }
    @Override
    public void invoke(Filter filter) {
        for (SQLActionExpression1<Filter> predicateSQLAction : whereActionList) {
            predicateSQLAction.apply(filter);
        }
    }
}
