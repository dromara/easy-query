package com.easy.query.test.listener;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecuteBeforeArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;

/**
 * create time 2023/11/11 23:13
 * 文件说明
 *
 * @author xuejiaming
 */
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
        ListenerContext listenContext = listenerContextManager.getListenContext();
        if(listenContext!=null){
            listenContext.setJdbcExecuteAfterArg(afterArg);
//            listenerContextManager.clear();
        }
    }
}
