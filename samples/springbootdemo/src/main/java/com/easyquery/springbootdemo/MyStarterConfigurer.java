package com.easyquery.springbootdemo;

import com.easy.query.core.bootstrapper.StarterConfigurer;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.inject.ServiceCollection;

/**
 * create time 2023/7/4 22:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyStarterConfigurer implements StarterConfigurer {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(NameConversion.class, MyNameConversion.class);
    }
}
