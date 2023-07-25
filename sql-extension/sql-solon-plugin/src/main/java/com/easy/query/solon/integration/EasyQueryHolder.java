package com.easy.query.solon.integration;

import com.easy.query.api.proxy.client.EasyProxyQuery;
import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import org.noear.solon.core.VarHolder;

/**
 * create time 2023/7/24 22:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyQueryHolder {
    EasyQueryClient getEasyQueryClient();
    EasyQuery getEasyQuery();
    EasyProxyQuery getEasyProxyQuery();

    void injectTo(VarHolder varH);
}
