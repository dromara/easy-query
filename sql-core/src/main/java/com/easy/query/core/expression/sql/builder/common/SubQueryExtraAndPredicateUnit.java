package com.easy.query.core.expression.sql.builder.common;

import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuesPredicate;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * create time 2025/8/24 08:34
 * 文件说明
 *
 * @author xuejiaming
 */
public class SubQueryExtraAndPredicateUnit extends AbstractSubQueryExtraPredicateUnit {

    private final AndPredicateSegment andPredicateSegment;
    private final List<SQLActionExpression1<WherePredicate<Object>>> whereActionList;
    protected boolean isInvoke;


    public SubQueryExtraAndPredicateUnit(AndPredicateSegment andPredicateSegment, TableAvailable fromTable, String[] selfProperties, String[] targetProperties, WherePredicate<Object> wherePredicate) {
        super(fromTable, selfProperties, targetProperties, wherePredicate);
        this.andPredicateSegment = andPredicateSegment;
        this.isInvoke = true;
        this.whereActionList = new ArrayList<>();
        parsePredicate();
    }

    private void parsePredicate() {
        List<Predicate> flatAndPredicates = andPredicateSegment.getFlatAndPredicates();
        if (EasyCollectionUtil.isNotEmpty(flatAndPredicates)) {
            for (Predicate predicate : flatAndPredicates) {
                SQLActionExpression1<WherePredicate<Object>> whereAction = parsePredicate(predicate);
                if (whereAction != null) {
                    this.whereActionList.add(whereAction);
                }
            }
        }
    }

    @Override
    public void invoke() {
        for (SQLActionExpression1<WherePredicate<Object>> predicateSQLAction : whereActionList) {
            predicateSQLAction.apply(wherePredicate);
        }
    }
}
