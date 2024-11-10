package com.easy.query.solon.integration.holder;

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
    <T> T getClient(Class<T> clazz);
    void injectTo(VarHolder varH);
}
