package com.easy.query.core.bootstrapper;

import com.easy.query.core.inject.ServiceCollection;

/**
 * create time 2023/5/10 13:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DatabaseConfiguration {
    void configure(ServiceCollection services);
}
