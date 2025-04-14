package com.easy.query.test.listener;

/**
 * create time 2023/11/11 23:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class ListenerContextManager {

    protected final ThreadLocal<ListenerContext> threadTx = ThreadLocal.withInitial(() -> null);

    public void startListen(ListenerContext listenerContext){
        threadTx.set(listenerContext);
    }

    public void startCreateListen(){
        threadTx.set(new ListenerContext());
    }

    public ListenerContext getListenContext(){
        return threadTx.get();
    }
    public void clear(){
        threadTx.remove();
    }
}
