//package com.easy.query.core.expression.predicate;
//
//import com.easy.query.core.expression.segment.condition.AbstractPredicateSegment;
//import com.easy.query.core.expression.segment.condition.OrPredicateSegment;
//import com.easy.query.core.expression.segment.condition.PredicateSegment;
//import com.easy.query.core.expression.segment.condition.predicate.ColumnTrueOrFalsePredicate;
//import com.easy.query.core.expression.segment.condition.predicate.Predicate;
//import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
//import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
//
//import java.util.Iterator;
//import java.util.List;
//
//public class RealSmartPredicateAnonymousExpressionBuilderProvider implements SmartPredicateAnonymousExpressionBuilderProvider {
//    private static final ColumnTrueOrFalsePredicate TRUE_PREDICATE = new ColumnTrueOrFalsePredicate(true, null, null);
//
//    @Override
//    public void process(AnonymousEntityTableExpressionBuilder anonymousEntityTableExpressionBuilder, PredicateSegment predicateSegment) {
//        processPredicateSegments(anonymousEntityTableExpressionBuilder.getEntityQueryExpressionBuilder(), (AbstractPredicateSegment) predicateSegment);
//    }
//
//    public boolean processPredicateSegments(EntityQueryExpressionBuilder entityQueryExpressionBuilder, AbstractPredicateSegment predicateSegment) {
//        Predicate predicate = predicateSegment.getPredicate();
//        List<PredicateSegment> children = predicateSegment.getChildren();
//        if (children != null && !children.isEmpty()) {
//            boolean isOrPredicateSegment = predicateSegment instanceof OrPredicateSegment || (children.get(children.size() - 1) instanceof OrPredicateSegment);
//            if (isOrPredicateSegment) {
//                return SmartPredicateUtils.parseOrPredicate(entityQueryExpressionBuilder, predicateSegment);
//            }
//            Iterator<PredicateSegment> iterator = children.iterator();
//            while (iterator.hasNext()) {
//                PredicateSegment child = iterator.next();
//                if (child instanceof AbstractPredicateSegment) {
//                    if (processPredicateSegments(entityQueryExpressionBuilder, (AbstractPredicateSegment) child)) {
//                        iterator.remove();
//                    }
//                }
//            }
//        }
//        if (predicate != null) {
//            if (SmartPredicateUtils.parseAndPredicate(entityQueryExpressionBuilder, predicate)) {
//                predicateSegment.reset();
//                predicateSegment.setPredicate(TRUE_PREDICATE);
//                return true;
//            }
//        }
//        return predicate == null && children != null && children.isEmpty();
//    }
//}
