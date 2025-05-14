package com.easy.query.solon.integration.holder;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyObjectUtil;
import org.noear.solon.core.VarHolder;

import java.util.Objects;

/**
 * create time 2023/7/24 22:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyQueryHolder implements EasyQueryHolder{

    private final EasyQueryClient easyQueryClient;
    private final EasyEntityQuery entityQuery;
//    private final EntityQuery entityQuery;

    public DefaultEasyQueryHolder(EasyQueryClient easyQueryClient, EasyEntityQuery entityQuery){
        this.easyQueryClient = easyQueryClient;
        this.entityQuery = entityQuery;

//        this.entityQuery = entityQuery;
    }

    @Override
    public EasyQueryClient getEasyQueryClient() {
        return easyQueryClient;
    }

    @Override
    public <T> T getClient(Class<T> clazz) {
        if (Objects.equals(EasyEntityQuery.class,clazz)) {
            return EasyObjectUtil.typeCastNullable(this.entityQuery);
        }

        if (Objects.equals(EasyQueryClient.class,clazz)) {
            return EasyObjectUtil.typeCastNullable(this.easyQueryClient);
        }
        throw new UnsupportedOperationException(EasyClassUtil.getSimpleName(clazz));
    }


//    @Override
//    public EntityQuery getEntityQuery() {
//        return entityQuery;
//    }

    @Override
    public void injectTo(VarHolder varH) {

        if (Objects.equals(EasyEntityQuery.class,varH.getType())) {
            varH.setValue(this.entityQuery);
            return;
        }

        if (Objects.equals(EasyQueryClient.class,varH.getType())) {
            varH.setValue(this.easyQueryClient);
            return;
        }
        if(Objects.equals(QueryConfiguration.class,varH.getType())){
            varH.setValue(this.easyQueryClient.getRuntimeContext().getQueryConfiguration());
        }
        if(Objects.equals(QueryRuntimeContext.class,varH.getType())){
            varH.setValue(this.easyQueryClient.getRuntimeContext());
        }

    }
}
