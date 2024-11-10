package com.easy.query.solon.integration.holder;

import com.easy.query.core.api.client.EasyQueryClient;

/**
 * create time 2024/11/10 09:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface HolderFactory {
    EasyQueryHolder getHolder(EasyQueryClient easyQueryClient);
}
