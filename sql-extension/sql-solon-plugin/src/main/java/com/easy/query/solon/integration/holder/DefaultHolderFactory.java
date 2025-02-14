package com.easy.query.solon.integration.holder;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.api4j.client.DefaultEasyQuery;
import com.easy.query.core.api.client.EasyQueryClient;

/**
 * create time 2024/11/10 09:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultHolderFactory implements HolderFactory {
    public static final HolderFactory DEFAULT = new DefaultHolderFactory();

    @Override
    public EasyQueryHolder getHolder(EasyQueryClient easyQueryClient) {
        EasyEntityQuery entityQuery = new DefaultEasyEntityQuery(easyQueryClient);
        DefaultEasyQuery easyQuery = new DefaultEasyQuery(easyQueryClient);

        return new DefaultEasyQueryHolder(easyQueryClient, entityQuery, easyQuery);
    }
}
