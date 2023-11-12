package com.easy.query.solon.sharding.demo;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecuteBeforeArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.inject.ServiceProvider;
import org.noear.solon.core.AppContext;

import java.util.List;

/**
 * create time 2023/11/12 00:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class LogSlowSQLListener implements JdbcExecutorListener {

    private final ServiceProvider serviceProvider;

    public LogSlowSQLListener(ServiceProvider serviceProvider){

        this.serviceProvider = serviceProvider;
    }
    @Override
    public boolean enable() {
        return true;//表示需要开启监听
    }

    @Override
    public void onExecuteBefore(JdbcExecuteBeforeArg arg) {
        //这边可以通过setState来传递参数
//        HashMap<String, Object> state = new HashMap<>();
//        arg.setState(state);
    }

    @Override
    public void onExecuteAfter(JdbcExecuteAfterArg afterArg) {
        //通过serviceProvider来获取注册的bean实例
        AppContext service = serviceProvider.getService(AppContext.class);
        HttpLogRequest httpLogRequest = service.getBean(HttpLogRequest.class);
        JdbcExecuteBeforeArg beforeArg = afterArg.getBeforeArg();
        //通过getState来获取before的参数
//        Map<String, Object> state = beforeArg.getState();
        //记录耗时操作
        long elapsed = afterArg.getEnd() - beforeArg.getStart();
        //耗时3秒以上的sql需要记录
        if(elapsed>=3*1000){
            //发送http请求

            String sql = beforeArg.getSql();
            List<List<SQLParameter>> sqlParameters = beforeArg.getSqlParameters();
        }
    }
}
