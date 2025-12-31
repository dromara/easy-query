package com.easy.query.core.expression.predicate;

import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
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
public class SmartPredicateAndUnit extends AbstractSmartPredicateUnit {
    private final AndPredicateSegment andPredicateSegment;
    private final List<SmartPredicateParseResult> parseFilterActionList;


    public SmartPredicateAndUnit(EntityQueryExpressionBuilder entityQueryExpressionBuilder, AndPredicateSegment andPredicateSegment, TableAvailable table, Map<String, SmartPredicateItem> aliasMap) {
        super(entityQueryExpressionBuilder, table, aliasMap);
        this.andPredicateSegment = andPredicateSegment;
        this.parseFilterActionList = new ArrayList<>();
        parsePredicate();
    }

    private void parsePredicate() {
        List<Predicate> flatAndPredicates = andPredicateSegment.getFlatAndPredicates();
        if (EasyCollectionUtil.isNotEmpty(flatAndPredicates)) {
            for (Predicate predicate : flatAndPredicates) {
                SmartPredicateParseResult parseFilterAction = parsePredicate(predicate);
                if (parseFilterAction != null) {
                    this.parseFilterActionList.add(parseFilterAction);
                }
            }
        }
    }

    @Override
    public void invoke(Filter filter) {
        for (SmartPredicateParseResult predicateSQLAction : parseFilterActionList) {
            TableAvailable parseTable = predicateSQLAction.smartPredicateItem.table;
            if (parseTable == entityQueryExpressionBuilder.getFromTable().getEntityTable()) {
                predicateSQLAction.filterAction.apply(filter);
            } else {
                boolean goOnRelationTable=true;
                for (EntityTableExpressionBuilder tableExpressionBuilder : entityQueryExpressionBuilder.getTables()) {
                    boolean ok = appendTableJoinOnPredicate(tableExpressionBuilder, predicateSQLAction);
                    if(ok){
                        goOnRelationTable=false;
                        break;
                    }
                }
                if(!goOnRelationTable){
                    for (Map.Entry<RelationTableKey, EntityTableExpressionBuilder> kv : entityQueryExpressionBuilder.getRelationTables().entrySet()) {
                        EntityTableExpressionBuilder tableExpressionBuilder = kv.getValue();
                        boolean ok = appendTableJoinOnPredicate(tableExpressionBuilder, predicateSQLAction);
                        if(ok){
                            break;
                        }
                    }
                }
            }
        }
    }
}
