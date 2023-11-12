package com.easyquery.springbootdemo;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecuteBeforeArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;

import java.util.List;

/**
 * create time 2023/11/12 00:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class LogSlowSQLListener implements JdbcExecutorListener {
    private final HttpLogRequest httpLogRequest;

    public LogSlowSQLListener(HttpLogRequest httpLogRequest){

        this.httpLogRequest = httpLogRequest;
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
