package com.easy.query.solon.integration;

import com.easy.query.api.proxy.client.EasyProxyQuery;
import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import org.noear.solon.core.VarHolder;

/**
 * create time 2023/7/24 22:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyQueryHolder implements EasyQueryHolder{

    private final EasyQueryClient easyQueryClient;
    private final EasyQuery easyQuery;
    private final EasyProxyQuery easyProxyQuery;

    public DefaultEasyQueryHolder(EasyQueryClient easyQueryClient, EasyQuery easyQuery, EasyProxyQuery easyProxyQuery){
        this.easyQueryClient = easyQueryClient;
        this.easyQuery = easyQuery;

        this.easyProxyQuery = easyProxyQuery;
    }

    @Override
    public EasyQueryClient getEasyQueryClient() {
        return easyQueryClient;
    }

    @Override
    public EasyQuery getEasyQuery() {
        return easyQuery;
    }

    @Override
    public EasyProxyQuery getEasyProxyQuery() {
        return easyProxyQuery;
    }

    @Override
    public void injectTo(VarHolder varH) {

        if (EasyQuery.class.isAssignableFrom(varH.getType())) {
            varH.setValue(this.easyQuery);
            return;
        }

        if (EasyQueryClient.class.isAssignableFrom(varH.getType())) {
            varH.setValue(this.easyQueryClient);
            return;
        }

        if (EasyProxyQuery.class.isAssignableFrom(varH.getType())) {
            varH.setValue(this.easyProxyQuery);
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
