package com.easy.query.solon.sharding.demo;

import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBuilderConfiguration;
import org.noear.solon.Solon;

/**
 * create time 2023/8/2 11:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class App {
    public static void main(String[] args) {
        Solon.start(App.class,args,app->{
            app.onEvent(EasyQueryBuilderConfiguration.class,e->{
//                HttpLogRequest httpLogRequest = app.context().getBean(HttpLogRequest.class);
                e.replaceService(app.context());
                e.replaceService(JdbcExecutorListener.class,LogSlowSQLListener.class);
            });
        });
    }
}
