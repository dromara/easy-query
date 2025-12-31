package com.easy.query.core.expression.predicate;

import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.OrPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * create time 2025/9/8 16:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSmartPredicateAnonymousExpressionBuilderProvider implements SmartPredicateAnonymousExpressionBuilderProvider {
    @Override
    public void process(AnonymousEntityTableExpressionBuilder anonymousEntityTableExpressionBuilder, PredicateSegment predicateSegment) {
        //(select * from a join b .....) t
        //这个就是上面的去掉括号 select * from a join b .....
        EntityQueryExpressionBuilder innerEntityQueryExpressionBuilder = anonymousEntityTableExpressionBuilder.getEntityQueryExpressionBuilder();
        TableAvailable entityTable = anonymousEntityTableExpressionBuilder.getEntityTable();

        //获取原先的from子查询有哪些可以被发现的条件
        Set<SmartPredicateItem> alreadyPredicateSet = getAlreadyPredicateSet(innerEntityQueryExpressionBuilder);
        Map<String, SmartPredicateItem> aliasMap = getAliasMap(innerEntityQueryExpressionBuilder, alreadyPredicateSet);

        List<PredicateSegment> flatAndPredicateSegments = predicateSegment.getFlatAndPredicateSegments();
        FilterImpl filter = new FilterImpl(innerEntityQueryExpressionBuilder.getRuntimeContext(), innerEntityQueryExpressionBuilder.getExpressionContext(), innerEntityQueryExpressionBuilder.getWhere(), false, innerEntityQueryExpressionBuilder.getExpressionContext().getValueFilter());
        getWhereExtraPredicateSegment(innerEntityQueryExpressionBuilder, flatAndPredicateSegments, entityTable, aliasMap, filter);

    }

    private Set<SmartPredicateItem> getAlreadyPredicateSet(EntityQueryExpressionBuilder innerEntityQueryExpressionBuilder) {

        //获取原先的from子查询有哪些可以被发现的条件
        Set<SmartPredicateItem> alreadyPredicateSet = new HashSet<>();
        List<PredicateSegment> innerWherePredicateSegments = innerEntityQueryExpressionBuilder.getWhere().getFlatAndPredicateSegments();
        for (PredicateSegment innerWherePredicateSegment : innerWherePredicateSegments) {

            if (innerWherePredicateSegment instanceof AndPredicateSegment) {
                AndPredicateSegment andPredicateSegment = (AndPredicateSegment) innerWherePredicateSegment;

                List<Predicate> flatAndPredicates = andPredicateSegment.getFlatAndPredicates();
                if (EasyCollectionUtil.isNotEmpty(flatAndPredicates)) {
                    for (Predicate flatAndPredicate : flatAndPredicates) {
                        TableAvailable table = flatAndPredicate.getTable();
                        String propertyName = flatAndPredicate.getPropertyName();
                        alreadyPredicateSet.add(new SmartPredicateItem(table, propertyName));
                    }
                }
            }
        }
        return alreadyPredicateSet;
    }

    private Map<String, SmartPredicateItem> getAliasMap(EntityQueryExpressionBuilder innerEntityQueryExpressionBuilder, Set<SmartPredicateItem> alreadyPredicateSet) {

        //获取select as的别名和实际表名和实际属性
        Map<String, SmartPredicateItem> aliasMap = new HashMap<>();
        for (SQLSegment sqlSegment : innerEntityQueryExpressionBuilder.getProjects().getSQLSegments()) {
            if (sqlSegment instanceof ColumnSegment) {
                ColumnSegment columnSegment = (ColumnSegment) sqlSegment;
                if (columnSegment.getAlias() != null) {
                    SmartPredicateItem smartPredicateItem = new SmartPredicateItem(columnSegment.getTable(), columnSegment.getPropertyName());
                    if (!alreadyPredicateSet.contains(smartPredicateItem)) {
                        aliasMap.put(columnSegment.getAlias(), smartPredicateItem);
                    }
                }
            }
        }
        return aliasMap;
    }

    private void getWhereExtraPredicateSegment(EntityQueryExpressionBuilder innerEntityQueryExpressionBuilder, List<PredicateSegment> flatAndPredicateSegments, TableAvailable fromTable, Map<String, SmartPredicateItem> aliasMap, Filter filter) {

        for (PredicateSegment predicateSegment : flatAndPredicateSegments) {
            if (predicateSegment instanceof AndPredicateSegment) {
                AndPredicateSegment andPredicateSegment = (AndPredicateSegment) predicateSegment;

                List<Predicate> flatAndPredicates = andPredicateSegment.getFlatAndPredicates();
                if (EasyCollectionUtil.isNotEmpty(flatAndPredicates)) {
                    SmartPredicateAndUnit smartPredicateAndUnit = new SmartPredicateAndUnit(innerEntityQueryExpressionBuilder, andPredicateSegment, fromTable, aliasMap);
                    smartPredicateAndUnit.invoke(filter);
                } else {

                    List<PredicateSegment> children = andPredicateSegment.getChildren();
                    if (children != null) {
                        boolean allMatch = children.stream().skip(1).allMatch(o -> o instanceof OrPredicateSegment);
                        if (allMatch) {
                            SmartPredicateOrUnit smartPredicateOrUnit = new SmartPredicateOrUnit(innerEntityQueryExpressionBuilder, andPredicateSegment, fromTable, aliasMap);
                            smartPredicateOrUnit.invoke(filter);
                        }
                    }
                }
            }
        }
    }
}
