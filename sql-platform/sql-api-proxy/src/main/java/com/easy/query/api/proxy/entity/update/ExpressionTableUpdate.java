//package com.easy.query.api.proxy.entity.update;
//
//import com.easy.query.core.basic.api.internal.ConfigureVersionable;
//import com.easy.query.core.basic.api.internal.WithVersionable;
//import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
//import com.easy.query.core.basic.api.update.Updatable;
//import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
//import com.easy.query.core.expression.sql.builder.ExpressionContext;
//import com.easy.query.core.proxy.ProxyEntity;
//import com.easy.query.core.proxy.SQLColumnSetExpression;
//import com.easy.query.core.proxy.SQLPredicateExpression;
//import com.easy.query.core.proxy.sql.ColumnSet;
//import com.easy.query.core.proxy.sql.Predicate;
//
//import java.util.Collection;
//
///**
// * create time 2023/12/8 13:21
// * 文件说明
// *
// * @author xuejiaming
// */
//public interface ExpressionTableUpdate<TProxy extends ProxyEntity<TProxy, T>, T> extends Updatable<T, ExpressionTableUpdate<TProxy,T>>, WithVersionable<ExpressionTableUpdate<TProxy,T>>, ConfigureVersionable<ExpressionTableUpdate<TProxy,T>> {
//    TProxy getProxy();
//    ClientExpressionUpdatable<T> getClientUpdate();
//
//    default ExpressionTableUpdate<TProxy,T> set(SQLColumnSetExpression... columnExpressions) {
//        return set(true,columnExpressions);
//    }
//
//    default ExpressionTableUpdate<TProxy,T> set(boolean condition, SQLColumnSetExpression... columnExpressions) {
//        if(condition){
//            SQLColumnSetExpression sqlColumnSetExpression = ColumnSet.setValues(columnExpressions);
//            sqlColumnSetExpression.accept(getClientUpdate().getColumnSetter().getSetter());
//        }
//        return this;
//    }
//
//    /**
//     * where(o.id().eq(),
//     *      o.id().eq(),
//     *      Predicate.or(
//     *          o.id().eq(),
//     *          o.id().eq()
//     *      ))
//     *
//     * @param whereExpressions
//     * @return
//     */
//    default ExpressionTableUpdate<TProxy,T> where(SQLPredicateExpression... whereExpressions) {
//        return where(true,whereExpressions);
//    }
//
//    /**
//     * where(o.id().eq(),
//     *      o.id().eq(),
//     *      Predicate.or(
//     *          o.id().eq(),
//     *          o.id().eq()
//     *      ))
//     * @param condition
//     * @param whereExpressions
//     * @return
//     */
//    default ExpressionTableUpdate<TProxy,T> where(boolean condition, SQLPredicateExpression... whereExpressions) {
//        if(condition){
//            getClientUpdate().where(true,where -> {
//                SQLPredicateExpression sqlPredicateExpression = Predicate.and(whereExpressions);
//                sqlPredicateExpression.accept(where.getFilter());
//            });
//        }
//        return this;
//    }
//
//    default ExpressionTableUpdate<TProxy,T> whereById(Object id) {
//        getClientUpdate().whereById(id);
//        return this;
//    }
//
//    default ExpressionTableUpdate<TProxy,T> whereById(boolean condition, Object id) {
//        getClientUpdate().whereById(condition, id);
//        return this;
//    }
//
//
//    default <TProperty> ExpressionTableUpdate<TProxy,T> whereByIds(Collection<TProperty> ids) {
//        getClientUpdate().whereByIds(ids);
//        return this;
//    }
//
//    default <TProperty> ExpressionTableUpdate<TProxy,T> whereByIds(boolean condition, Collection<TProperty> ids) {
//        getClientUpdate().whereByIds(condition, ids);
//        return this;
//    }
//
//    default ExpressionContext getExpressionContext() {
//        return getClientUpdate().getExpressionContext();
//    }
//
//    default String toSQL() {
//        return getClientUpdate().toSQL();
//    }
//
//    default String toSQL(ToSQLContext toSQLContext) {
//        return getClientUpdate().toSQL(toSQLContext);
//    }
//}
