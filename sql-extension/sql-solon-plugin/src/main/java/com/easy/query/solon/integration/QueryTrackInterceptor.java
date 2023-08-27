package com.easy.query.solon.integration;

import com.easy.query.core.annotation.EasyQueryTrack;
import com.easy.query.core.basic.extension.track.InvokeTryFinally;
import org.noear.solon.core.aspect.Interceptor;
import org.noear.solon.core.aspect.Invocation;

import java.util.function.Supplier;

/**
 * create time 2023/7/25 12:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTrackInterceptor implements Interceptor {

    @Override
    public Object doIntercept(Invocation inv) throws Throwable {
        EasyQueryTrack easyQueryTrack =inv.method().getAnnotation(EasyQueryTrack.class); //通过反射拿到注解对象
        if (easyQueryTrack!=null&&easyQueryTrack.enable()) {

            InvokeTryFinally trackInvokeTryFinally = DbManager.global().getTrackInvokeTryFinally(easyQueryTrack.tag());
            try {
                trackInvokeTryFinally.begin();
                return inv.invoke();
            } finally {
                trackInvokeTryFinally.release();
            }
        } else {
            return inv.invoke();
        }
    }
}
