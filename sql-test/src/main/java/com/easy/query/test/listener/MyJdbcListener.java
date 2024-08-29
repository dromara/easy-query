package com.easy.query.test.listener;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecuteBeforeArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.util.EasySQLUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * create time 2023/11/11 23:13
 * 文件说明
 *
 * @author xuejiaming
 */
@Slf4j
public class MyJdbcListener implements JdbcExecutorListener {
    private final ListenerContextManager listenerContextManager;

    public MyJdbcListener(ListenerContextManager listenerContextManager){

        this.listenerContextManager = listenerContextManager;
    }
    @Override
    public boolean enable() {
        return true;
    }

    @Override
    public void onExecuteBefore(JdbcExecuteBeforeArg arg) {

    }

    @Override
    public void onExecuteAfter(JdbcExecuteAfterArg afterArg) {
//        ExecuteMethodEnum executeMethod = afterArg.getBeforeArg().getExecuteMethod();

        ListenerContext listenContext = listenerContextManager.getListenContext();
        if(listenContext!=null){
            listenContext.record(afterArg);
//            listenerContextManager.clear();
        }
    }
}
