package com.easy.query.solon.integration.holder;

import com.easy.query.api.proxy.client.EasyProxyQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.api4kt.client.EasyKtQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyObjectUtil;
import org.noear.solon.core.VarHolder;

/**
 * create time 2023/7/24 22:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyQueryHolder implements EasyQueryHolder{

    private final EasyQueryClient easyQueryClient;
    private final EasyEntityQuery entityQuery;
    private final EasyQuery easyQuery;
    private final EasyProxyQuery easyProxyQuery;
    private final EasyKtQuery easyKtQuery;
//    private final EntityQuery entityQuery;

    public DefaultEasyQueryHolder(EasyQueryClient easyQueryClient, EasyEntityQuery entityQuery, EasyQuery easyQuery, EasyProxyQuery easyProxyQuery, EasyKtQuery easyKtQuery){
        this.easyQueryClient = easyQueryClient;
        this.entityQuery = entityQuery;
        this.easyQuery = easyQuery;

        this.easyProxyQuery = easyProxyQuery;
        this.easyKtQuery = easyKtQuery;
//        this.entityQuery = entityQuery;
    }

    @Override
    public EasyQueryClient getEasyQueryClient() {
        return easyQueryClient;
    }

    @Override
    public <T> T getClient(Class<T> clazz) {
        if (EasyEntityQuery.class.isAssignableFrom(clazz)) {
            return EasyObjectUtil.typeCastNullable(this.entityQuery);
        }

        if (EasyQuery.class.isAssignableFrom(clazz)) {
            return EasyObjectUtil.typeCastNullable(this.easyQuery);
        }
//        if(EntityQuery.class.isAssignableFrom(varH.getType())){
//            varH.setValue(this.entityQuery);
//            return;
//        }

        if (EasyQueryClient.class.isAssignableFrom(clazz)) {
            return EasyObjectUtil.typeCastNullable(this.easyQueryClient);
        }

        if (EasyProxyQuery.class.isAssignableFrom(clazz)) {
            return EasyObjectUtil.typeCastNullable(this.easyProxyQuery);
        }

        if (EasyKtQuery.class.isAssignableFrom(clazz)) {
            return EasyObjectUtil.typeCastNullable(this.easyKtQuery);
        }
        throw new UnsupportedOperationException(EasyClassUtil.getSimpleName(clazz));
    }


//    @Override
//    public EntityQuery getEntityQuery() {
//        return entityQuery;
//    }

    @Override
    public void injectTo(VarHolder varH) {

        if (EasyEntityQuery.class.isAssignableFrom(varH.getType())) {
            varH.setValue(this.entityQuery);
            return;
        }

        if (EasyQuery.class.isAssignableFrom(varH.getType())) {
            varH.setValue(this.easyQuery);
            return;
        }
//        if(EntityQuery.class.isAssignableFrom(varH.getType())){
//            varH.setValue(this.entityQuery);
//            return;
//        }

        if (EasyQueryClient.class.isAssignableFrom(varH.getType())) {
            varH.setValue(this.easyQueryClient);
            return;
        }

        if (EasyProxyQuery.class.isAssignableFrom(varH.getType())) {
            varH.setValue(this.easyProxyQuery);
            return;
        }

        if (EasyKtQuery.class.isAssignableFrom(varH.getType())) {
            varH.setValue(this.easyKtQuery);
            return;
        }
        if(QueryConfiguration.class.isAssignableFrom(varH.getType())){
            varH.setValue(this.easyQueryClient.getRuntimeContext().getQueryConfiguration());
        }
        if(QueryRuntimeContext.class.isAssignableFrom(varH.getType())){
            varH.setValue(this.easyQueryClient.getRuntimeContext());
        }

    }
}
