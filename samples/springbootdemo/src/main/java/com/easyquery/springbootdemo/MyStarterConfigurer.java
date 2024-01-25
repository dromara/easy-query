package com.easyquery.springbootdemo;

import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.StarterConfigurer;
import com.easy.query.core.inject.ServiceCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * create time 2023/7/4 22:33
 * 文件说明
 *
 * @author xuejiaming
 */
@Component
public class MyStarterConfigurer implements StarterConfigurer {
    @Autowired
    private HttpLogRequest httpLogRequest;
    @Override
    public void configure(ServiceCollection services) {
//        services.addService(NameConversion.class, MyNameConversion.class);
        services.addService(httpLogRequest);//直接注册实例到easy-query内部的依赖注入容器里面
        services.addService(JdbcExecutorListener.class, LogSlowSQLListener.class);
    }
}
