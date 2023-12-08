//package com.easy.query.api.proxy.entity.update.abstraction;
//
//import com.easy.query.api.proxy.entity.update.ExpressionTableUpdate;
//import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
//import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
//import com.easy.query.core.proxy.ProxyEntity;
//
//import java.util.function.Function;
//
///**
// * create time 2023/12/8 13:30
// * 文件说明
// *
// * @author xuejiaming
// */
//public class AbstractExpressionTableUpdatable<TProxy extends ProxyEntity<TProxy, T>, T> implements ExpressionTableUpdate<TProxy,T> {
//    private final TProxy tProxy;
//    private final ClientExpressionUpdatable<T> clientExpressionUpdatable;
//
//    public AbstractExpressionTableUpdatable(TProxy tProxy, ClientExpressionUpdatable<T> clientExpressionUpdatable){
//
//        this.tProxy = tProxy.create(clientExpressionUpdatable.getUpdateExpressionBuilder().getTable(0).getEntityTable());
//        this.clientExpressionUpdatable = clientExpressionUpdatable;
//    }
//    @Override
//    public ExpressionTableUpdate<TProxy, T> ignoreVersion(boolean ignored) {
//        clientExpressionUpdatable.ignoreVersion(ignored);
//        return this;
//    }
//
//    @Override
//    public ExpressionTableUpdate<TProxy, T> noInterceptor() {
//        clientExpressionUpdatable.noInterceptor();
//        return this;
//    }
//
//    @Override
//    public ExpressionTableUpdate<TProxy, T> useInterceptor(String name) {
//        clientExpressionUpdatable.useInterceptor(name);
//        return this;
//    }
//
//    @Override
//    public ExpressionTableUpdate<TProxy, T> noInterceptor(String name) {
//        clientExpressionUpdatable.noInterceptor(name);
//        return this;
//    }
//
//    @Override
//    public ExpressionTableUpdate<TProxy, T> useInterceptor() {
//        clientExpressionUpdatable.useInterceptor();
//        return this;
//    }
//
//    @Override
//    public ExpressionTableUpdate<TProxy, T> useLogicDelete(boolean enable) {
//        clientExpressionUpdatable.useLogicDelete(enable);
//        return this;
//    }
//
//    @Override
//    public ExpressionTableUpdate<TProxy, T> batch(boolean use) {
//        clientExpressionUpdatable.batch(use);
//        return this;
//    }
//
//    @Override
//    public void executeRows(long expectRows, String msg, String code) {
//         clientExpressionUpdatable.executeRows(expectRows,msg,code);
//    }
//
//    @Override
//    public long executeRows() {
//        return clientExpressionUpdatable.executeRows();
//    }
//
//    @Override
//    public ExpressionTableUpdate<TProxy, T> asTable(Function<String, String> tableNameAs) {
//        clientExpressionUpdatable.asTable(tableNameAs);
//        return this;
//    }
//
//    @Override
//    public ExpressionTableUpdate<TProxy, T> asSchema(Function<String, String> schemaAs) {
//        clientExpressionUpdatable.asSchema(schemaAs);
//        return this;
//    }
//
//    @Override
//    public ExpressionTableUpdate<TProxy, T> asAlias(String alias) {
//        clientExpressionUpdatable.asAlias(alias);
//        return this;
//    }
//
//    @Override
//    public ExpressionTableUpdate<TProxy, T> asTableLink(Function<String, String> linkAs) {
//        clientExpressionUpdatable.asTableLink(linkAs);
//        return this;
//    }
//
//    @Override
//    public ExpressionTableUpdate<TProxy, T> withVersion(boolean condition, Object versionValue) {
//        clientExpressionUpdatable.withVersion(condition,versionValue);
//        return this;
//    }
//
//    @Override
//    public EntityUpdateExpressionBuilder getUpdateExpressionBuilder() {
//        return clientExpressionUpdatable.getUpdateExpressionBuilder();
//    }
//
//    @Override
//    public TProxy getProxy() {
//        return tProxy;
//    }
//
//    @Override
//    public ClientExpressionUpdatable<T> getClientUpdate() {
//        return clientExpressionUpdatable;
//    }
//}
