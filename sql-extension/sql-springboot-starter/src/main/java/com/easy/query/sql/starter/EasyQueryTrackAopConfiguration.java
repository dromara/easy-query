package com.easy.query.sql.starter;

import com.easy.query.core.annotation.EasyQueryTrack;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.track.InvokeTryFinally;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.common.EasyQueryTrackInvoker;
import com.easy.query.core.common.EmptyInvokeTryFinally;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.sql.starter.config.EasyQueryTrackProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author xuejiaming
 * @FileName: EasyQueryTrackAopConfiguration.java
 * @Description: 文件说明
 * create time 2023/3/20 20:58
 */
@Aspect
@Configuration
@EnableConfigurationProperties(EasyQueryTrackProperties.class)
@ConditionalOnProperty(
        name = "easy-query-track.enable",
        matchIfMissing = true, havingValue = "true"
)
public class EasyQueryTrackAopConfiguration {
    private static final Log log = LogFactory.getLog(EasyQueryTrackAopConfiguration.class);
    private final Map<String, TrackManager> trackManagerMap = new HashMap<>();
    private final InvokeTryFinally allInvokeTryFinally;

    public EasyQueryTrackAopConfiguration(ApplicationContext applicationContext) {
        Map<String, EasyQueryClient> easyQueryClientMap = applicationContext.getBeansOfType(EasyQueryClient.class);
        Set<TrackManager> distinct = new LinkedHashSet<>();
        for (Map.Entry<String, EasyQueryClient> easyQueryClientEntry : easyQueryClientMap.entrySet()) {
            TrackManager trackManager = easyQueryClientEntry.getValue().getRuntimeContext().getTrackManager();
            distinct.add(trackManager);
            trackManagerMap.put(easyQueryClientEntry.getKey(), trackManager);
        }
        InvokeTryFinally invokeTryFinally= EmptyInvokeTryFinally.EMPTY;
        for (TrackManager trackManager : distinct) {
            invokeTryFinally=new EasyQueryTrackInvoker(invokeTryFinally,trackManager);
        }
        this.allInvokeTryFinally=invokeTryFinally;
    }

    @Around("execution(public * *(..)) && @annotation(com.easy.query.core.annotation.EasyQueryTrack)")
    public Object easyQueryTrack(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        EasyQueryTrack easyQueryTrack = method.getAnnotation(EasyQueryTrack.class); //通过反射拿到注解对象
        if (easyQueryTrack != null && easyQueryTrack.enable()) {
            InvokeTryFinally trackInvokeTryFinally = getTrackInvokeTryFinally(easyQueryTrack.tag());
            try {
                trackInvokeTryFinally.begin();
                return pjp.proceed();
            } finally {
                trackInvokeTryFinally.release();
            }
        } else {
            return pjp.proceed();
        }
    }

    private InvokeTryFinally getTrackInvokeTryFinally(String tag) {
        InvokeTryFinally invokeTryFinally= EmptyInvokeTryFinally.EMPTY;
        if (EasyStringUtil.isBlank(tag)) {
            return allInvokeTryFinally;
        }
        if (tag.contains(",")) {
            String[] names = tag.split(",");
            for (String name : names) {
                TrackManager trackManager = trackManagerMap.get(name);
                if (trackManager == null) {
                    log.warn("can not be found tag:[" + tag + "],track manager");
                    continue;
                }
                invokeTryFinally=new EasyQueryTrackInvoker(invokeTryFinally,trackManager);
            }
            return invokeTryFinally;

        } else {
            TrackManager trackManager = trackManagerMap.get(tag);
            if (trackManager == null) {
                log.warn("can not be found tag:[" + tag + "],track manager");
                return EmptyInvokeTryFinally.EMPTY;
            }
            return new EasyQueryTrackInvoker(invokeTryFinally,trackManager);
        }

    }
}
