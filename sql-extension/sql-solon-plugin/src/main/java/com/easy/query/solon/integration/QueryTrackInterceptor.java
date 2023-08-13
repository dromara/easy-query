package com.easy.query.solon.integration;

import com.easy.query.core.annotation.EasyQueryTrack;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import org.noear.solon.core.AopContext;
import org.noear.solon.core.aspect.Interceptor;
import org.noear.solon.core.aspect.Invocation;

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

            EasyQueryClient easyQueryClient = DbManager.global().getInstance(easyQueryTrack.tag(),EasyQueryClient.class);
            if(easyQueryClient==null){
                throw new EasyQueryInvalidOperationException("EasyQueryTrack tag:"+easyQueryTrack.tag()+" cant get EasyQueryClient");
            }
            TrackManager trackManager = easyQueryClient.getRuntimeContext().getTrackManager();
            try {
                trackManager.begin();
                return inv.invoke();
            } finally {
                trackManager.release();
            }

        } else {
            return inv.invoke();
        }
    }
}
