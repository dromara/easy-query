//package com.easy.query.core.proxy.columns;
//
//import com.easy.query.api.proxy.entity.select.EntityQueryable;
//import com.easy.query.core.basic.api.internal.ExpressionConfigurable;
//import com.easy.query.core.context.QueryRuntimeContext;
//import com.easy.query.core.expression.lambda.SQLExpression1;
//import com.easy.query.core.expression.parser.core.available.TableAvailable;
//import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
//import com.easy.query.core.proxy.ProxyEntity;
//import com.easy.query.core.proxy.core.EntitySQLContext;
//
///**
// * create time 2025/3/11 21:39
// * 文件说明
// *
// * @author xuejiaming
// */
//public class GroupingQueryContext<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
//    private final EntityExpressionBuilder entityExpressionBuilder;
//    private final EntitySQLContext entitySQLContext;
//    private final TableAvailable leftTable;
//    private final T1Proxy propertyProxy;
//    private SQLExpression1<T1Proxy> whereExpression;
//    private SQLExpression1<T1Proxy> orderByExpression;
//
//    public GroupingQueryContext(EntitySQLContext entitySQLContext, TableAvailable leftTable,  T1Proxy propertyProxy) {
//        this.entityExpressionBuilder = entitySQLContext.getEntityExpressionBuilder();
//        this.entitySQLContext = entitySQLContext;
//        this.leftTable = leftTable;
//        this.propertyProxy = propertyProxy;
//
//    }
//
//    public EntityExpressionBuilder getEntityExpressionBuilder() {
//        return entityExpressionBuilder;
//    }
//
//    public EntitySQLContext getEntitySQLContext() {
//        return entitySQLContext;
//    }
//
////    public EntitySQLContext getManyJoinEntitySQLContext() {
////        return manyJoinEntitySQLContext;
////    }
//
//    public QueryRuntimeContext getRuntimeContext() {
//        return entityExpressionBuilder.getRuntimeContext();
//    }
//
//
//    public T1Proxy getPropertyProxy() {
//        return propertyProxy;
//    }
//
//
//    public TableAvailable getLeftTable() {
//        return leftTable;
//    }
//
//    public SQLExpression1<T1Proxy> getWhereExpression() {
//        return whereExpression;
//    }
//
//    public SQLExpression1<T1Proxy> getOrderByExpression() {
//        return orderByExpression;
//    }
//
//    public void appendWhereExpression(SQLExpression1<T1Proxy> whereExpression) {
//        if (whereExpression != null) {
//            if (this.whereExpression == null) {
//                this.whereExpression = whereExpression;
//            } else {
//                SQLExpression1<T1Proxy> preWhereExpression = this.whereExpression;
//                this.whereExpression = o -> {
//                    preWhereExpression.apply(o);
//                    whereExpression.apply(o);
//                };
//            }
//        }
//    }
//
//    public void appendOrderByExpression(SQLExpression1<T1Proxy> orderByExpression) {
//        if (orderByExpression != null) {
//            if (this.orderByExpression == null) {
//                this.orderByExpression = orderByExpression;
//            } else {
//                SQLExpression1<T1Proxy> preOrderByExpression = this.orderByExpression;
//                this.orderByExpression = o -> {
//                    preOrderByExpression.apply(o);
//                    orderByExpression.apply(o);
//                };
//            }
//        }
//    }
//}
