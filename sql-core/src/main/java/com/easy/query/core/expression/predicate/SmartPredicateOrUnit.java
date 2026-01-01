package com.easy.query.core.expression.predicate;

import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/9/8 16:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class SmartPredicateOrUnit extends AbstractSmartPredicateUnit {
    private final AndPredicateSegment andPredicateSegment;
    private final List<SmartPredicateParseResult> parseFilterActionList;
    protected boolean isInvoke;


    public SmartPredicateOrUnit(EntityQueryExpressionBuilder entityQueryExpressionBuilder, AndPredicateSegment andPredicateSegment, TableAvailable table, Map<String, SmartPredicateItem> aliasMap) {
        super(entityQueryExpressionBuilder, table, aliasMap);
        this.andPredicateSegment = andPredicateSegment;
        this.parseFilterActionList = new ArrayList<>();
        this.isInvoke = true;
        parsePredicate();
    }

    @Override
    protected boolean predicateParseMatch(SmartPredicateItem smartPredicateItem) {
        return entityQueryExpressionBuilder.getFromTable().getEntityTable() == smartPredicateItem.table;
    }

    private void parsePredicate() {
        List<PredicateSegment> children = andPredicateSegment.getChildren();
        for (PredicateSegment predicateSegment : children) {
            if (this.isInvoke) {//or里面必须所有的都是这个条件才可以
                List<Predicate> flatAndPredicates = predicateSegment.getFlatAndPredicates();
                if (EasyCollectionUtil.isSingle(flatAndPredicates)) {
                    Predicate first = EasyCollectionUtil.first(flatAndPredicates);

                    SmartPredicateParseResult parseFilterAction = parsePredicate(first);
                    if (parseFilterAction != null) {
                        this.parseFilterActionList.add(parseFilterAction);
                    } else {
                        this.isInvoke = false;
                    }
                } else {
                    this.isInvoke = false;
                }
            }
        }
    }

    private EntityTableExpressionBuilder getJoinTableBuilder(TableAvailable parseTable) {
        EntityTableExpressionBuilder joinOnTableBuilder = entityQueryExpressionBuilder.getTables().stream().filter(tb -> tb.getEntityTable() == parseTable).findFirst().orElse(null);
        if (joinOnTableBuilder != null) {
            return joinOnTableBuilder;
        }
        return entityQueryExpressionBuilder.getRelationTables().values().stream().filter(tb -> tb.getEntityTable() == parseTable).findFirst().orElse(null);
    }

    @Override
    public void invoke(Filter filter) {
        if (this.isInvoke) {
            if (EasyCollectionUtil.isNotEmpty(parseFilterActionList)) {
                Map<Filter, List<SmartPredicateParseResult>> orPredicateMap = new LinkedHashMap<>();
                Map<EntityTableExpressionBuilder, Filter> tableBuilderFilterMap = new LinkedHashMap<>();
                for (SmartPredicateParseResult predicateSQLAction : parseFilterActionList) {
                    TableAvailable parseTable = predicateSQLAction.smartPredicateItem.table;
                    if (parseTable == entityQueryExpressionBuilder.getFromTable().getEntityTable()) {
                        List<SmartPredicateParseResult> smartPredicateParseResults = orPredicateMap.computeIfAbsent(filter, k -> new ArrayList<>());
                        smartPredicateParseResults.add(predicateSQLAction);
                    } else {
                        EntityTableExpressionBuilder joinOnTableBuilder = getJoinTableBuilder(parseTable);
                        if (joinOnTableBuilder != null) {
                            Filter joinFilter = tableBuilderFilterMap.computeIfAbsent(joinOnTableBuilder, k -> new FilterImpl(entityQueryExpressionBuilder.getRuntimeContext(), entityQueryExpressionBuilder.getExpressionContext(), joinOnTableBuilder.getOn(), false, entityQueryExpressionBuilder.getExpressionContext().getValueFilter()));
                            List<SmartPredicateParseResult> smartPredicateParseResults = orPredicateMap.computeIfAbsent(joinFilter, k -> new ArrayList<>());
                            smartPredicateParseResults.add(predicateSQLAction);
                        }
                    }
                }

                for (Map.Entry<Filter, List<SmartPredicateParseResult>> kv : orPredicateMap.entrySet()) {
                    Filter outFilter = kv.getKey();
                    List<SmartPredicateParseResult> orResults = kv.getValue();
                    outFilter.and(f -> {
                        for (SmartPredicateParseResult predicateSQLAction : orResults) {
                            predicateSQLAction.filterAction.apply(f);
                            f.or();
                        }
                    });
                }

            }
        }
    }
}
