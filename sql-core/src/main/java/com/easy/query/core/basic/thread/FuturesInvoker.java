package com.easy.query.core.basic.thread;

import com.easy.query.core.exception.EasyQueryFutureInvokeSQLException;
import com.easy.query.core.exception.EasyQueryTimeoutSQLException;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * create time 2023/5/26 09:31
 * 多线程future集合结果获取
 *
 * @author xuejiaming
 */
public class FuturesInvoker<TResult> implements Closeable {
    private final List<Future<TResult>> futures;

    public FuturesInvoker(List<Future<TResult>> futures){

        this.futures = futures;
    }


    /**
     * 获取多个future运行结果
     * @param timeoutMillis
     * @return
     * @throws SQLException {@link EasyQueryTimeoutSQLException} 超时 {@link EasyQueryFutureInvokeSQLException} 多线程运行错误或者失败
     */
    public List<TResult> get(final long timeoutMillis) throws SQLException {

        List<TResult> results = new ArrayList<>(futures.size());
        long start = System.currentTimeMillis();
        long constTime = 0L;
        for (Future<TResult> future : futures) {
            try {
                //如果第一个时间刚好卡点其实后面的都已经执行完了就不在需要计算了
                if(future.isDone()){
                    results.add(future.get());
                    continue;
                }
                if (timeoutMillis < constTime) {
                    throw new EasyQueryTimeoutSQLException("sharding query time out");
                }
                results.add(future.get(timeoutMillis - constTime, TimeUnit.MILLISECONDS));
                constTime = System.currentTimeMillis() - start;
            } catch (InterruptedException | ExecutionException e) {
                throw new EasyQueryFutureInvokeSQLException(e);
            } catch (TimeoutException e) {
                throw new EasyQueryTimeoutSQLException(e);
            }
        }
        return results;
    }

    @Override
    public void close() {
        for (Future<TResult> future : futures) {
            if(!future.isDone()){
                future.cancel(true);
            }
        }
    }
}
