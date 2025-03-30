//package com.easy.query.core.proxy.predicate;
//
//import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
//import com.easy.query.core.func.SQLFunc;
//import com.easy.query.core.func.SQLFunction;
//import com.easy.query.core.proxy.TablePropColumn;
//import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
//import com.easy.query.core.proxy.impl.SQLPredicateImpl;
//
///**
// * create time 2023/12/6 21:24
// * 文件说明
// *
// * @author xuejiaming
// */
//public interface DSLBooleanAssertPredicate<TProperty> extends TablePropColumn, EntitySQLContextAvailable {
//    default void isTrue() {
//        isTrue(true);
//    }
//
//    default void isTrue(boolean condition){
//        if(condition){
//           getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
//               f.eq(getTable(),getValue(),true);
//           }));
//        }
//    }
//    default void isNotTrue() {
//        isNotTrue(true);
//    }
//
//    default void isNotTrue(boolean condition){
//        if(condition){
//           getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
//               f.and(filter->{
//                   f.eq(getTable(),getValue(),false).or()
//                           .isNotNull(getTable(),getValue());
//               });
//           }));
//        }
//    }
//    default void isBank() {
//         isBank(true);
//    }
//
//    default void isBank(boolean condition){
//        if(condition){
//            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
//                SQLFunc fx = f.getRuntimeContext().fx();
//                SQLFunction bank = fx.bank(getValue());
//                f.sqlNativeSegment(bank.sqlSegment(getTable()),c->{
//                    bank.consume(new SQLNativeChainExpressionContextImpl(getTable(),c));
//                });
//            }));
//        }
//    }
//    default void isNotBank() {
//         isNotBank(true);
//    }
//
//    default void isNotBank(boolean condition){
//        if(condition){
//           getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
//               SQLFunc fx = f.getRuntimeContext().fx();
//               SQLFunction bank = fx.notBank(getValue());
//               f.sqlNativeSegment(bank.sqlSegment(getTable()),c->{
//                   bank.consume(new SQLNativeChainExpressionContextImpl(getTable(),c));
//               });
//           }));
//        }
//    }
//}
