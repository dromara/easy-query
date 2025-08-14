//package com.easy.query.core.proxy.sql;
//
//import com.easy.query.core.expression.lambda.SQLActionExpression1;
//import com.easy.query.core.expression.parser.core.available.IncludeAvailable;
//import com.easy.query.core.proxy.ProxyEntity;
//import com.easy.query.core.proxy.ProxyEntityAvailable;
//import com.easy.query.core.proxy.columns.SQLQueryable;
//import com.easy.query.core.proxy.columns.SubQueryContext;
//
//
///**
// * create time 2025/8/14 19:06
// * 文件说明
// *
// * @author xuejiaming
// */
//public class NavigatePathInclude<TProxy extends ProxyEntity<TProxy, TProp>, TProp> {
//
//    public final TProxy root;
//    private Include<?, ?> pathRoot;
//
//    public NavigatePathInclude(TProxy tProxy) {
//        this.root = tProxy;
//    }
//
//    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> Include<TPropertyProxy, TProperty> path(SQLQueryable<TPropertyProxy, TProperty> includeMany) {
//        Include<TPropertyProxy, TProperty> include = new Include<>(includeMany);
//        if (pathRoot == null) {
//            pathRoot = include;
//        }else{
//            pathRoot.thenInclude(includeMany)
//        }
//        SubQueryContext<TPropertyProxy, TProperty> subQueryContext = includeMany.getSubQueryContext();
//        SQLActionExpression1<TPropertyProxy> whereExpression = subQueryContext.getWhereExpression();
//        if(whereExpression!=null){
//            include.where(whereExpression);
//        }
//        SQLActionExpression1<TPropertyProxy> orderByExpression = subQueryContext.getOrderByExpression();
//        if(orderByExpression!=null){
//            include.orderBy(orderByExpression);
//        }
//        if(subQueryContext.hasElements()){
//            long limit = subQueryContext.getLimit();
//            long offset = subQueryContext.getOffset();
//            include.limit(offset,limit);
//        }
//        return include;
//    }
//
//    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> Include<TPropertyProxy, TProperty> path(TPropertyProxy includeOne) {
//        Include<TPropertyProxy, TProperty> include = new Include<>(includeOne);
//        if (pathRoot == null) {
//            pathRoot = include;
//        }
//        return include;
//    }
//
//    public IncludeAvailable getIncludeSelector() {
//        return pathRoot;
//    }
//}
