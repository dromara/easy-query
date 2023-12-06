//package com.easy.query.core.proxy.extension;
//
//import com.easy.query.core.expression.parser.core.SQLTableOwner;
//import com.easy.query.core.func.SQLFunc;
//import com.easy.query.core.proxy.SQLPredicateExpression;
//import com.easy.query.core.proxy.impl.SQLPredicateImpl;
//import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
//
///**
// * create time 2023/12/2 14:18
// * 文件说明
// *
// * @author xuejiaming
// */
//public interface ColumnFunctionPredicate<TProperty> extends SQLTableOwner, DSLSQLFunctionAvailable {
//    default SQLPredicateExpression ge(ColumnFunctionPredicate<TProperty> column) {
//        return ge(true, column);
//    }
//
//    default SQLPredicateExpression ge(boolean condition, ColumnFunctionPredicate<TProperty> column) {
//        if (condition) {
//            return new SQLPredicateImpl(f -> {
//                SQLFunc fx = f.getRuntimeContext().fx();
//                f.ge(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
//            });
//        }
//        return SQLPredicateExpression.empty;
//    }
//    default SQLPredicateExpression gt(ColumnFunctionPredicate<TProperty> column) {
//        return gt(true, column);
//    }
//
//    default SQLPredicateExpression gt(boolean condition, ColumnFunctionPredicate<TProperty> column) {
//        if (condition) {
//            return new SQLPredicateImpl(f -> {
//                SQLFunc fx = f.getRuntimeContext().fx();
//                f.gt(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
//            });
//        }
//        return SQLPredicateExpression.empty;
//    }
//
//
//    default SQLPredicateExpression eq(ColumnFunctionPredicate<TProperty> column) {
//        return eq(true, column);
//    }
//
//    default SQLPredicateExpression eq(boolean condition, ColumnFunctionPredicate<TProperty> column) {
//        if (condition) {
//            return new SQLPredicateImpl(f -> {
//                SQLFunc fx = f.getRuntimeContext().fx();
//                f.eq(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
//            });
//        }
//        return SQLPredicateExpression.empty;
//    }
//
//
//
//    default SQLPredicateExpression ne(ColumnFunctionPredicate<TProperty> column) {
//        return ne(true, column);
//    }
//
//    default SQLPredicateExpression ne(boolean condition, ColumnFunctionPredicate<TProperty> column) {
//        if (condition) {
//            return new SQLPredicateImpl(f -> {
//                SQLFunc fx = f.getRuntimeContext().fx();
//                f.ne(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
//            });
//        }
//        return SQLPredicateExpression.empty;
//    }
//
//    default SQLPredicateExpression le(ColumnFunctionPredicate<TProperty> column) {
//        return le(true, column);
//    }
//
//    default SQLPredicateExpression le(boolean condition, ColumnFunctionPredicate<TProperty> column) {
//        if (condition) {
//            return new SQLPredicateImpl(f -> {
//                SQLFunc fx = f.getRuntimeContext().fx();
//                f.le(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
//            });
//        }
//        return SQLPredicateExpression.empty;
//    }
//
//    default SQLPredicateExpression lt(ColumnFunctionPredicate<TProperty> column) {
//        return lt(true, column);
//    }
//
//    default SQLPredicateExpression lt(boolean condition, ColumnFunctionPredicate<TProperty> column) {
//        if (condition) {
//            return new SQLPredicateImpl(f -> {
//                SQLFunc fx = f.getRuntimeContext().fx();
//                f.lt(this.getTable(), func().apply(fx), column.getTable(), column.func().apply(fx));
//            });
//        }
//        return SQLPredicateExpression.empty;
//    }
//
//}
