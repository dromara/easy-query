package com.easy.query.sql.starter;

import com.easy.query.core.annotation.EasyQueryTrack;
import com.easy.query.core.track.TrackManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * @FileName: EasyQueryTrackAopConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/20 20:58
 * @Created by xuejiaming
 */
@Aspect
@Configuration
public class EasyQueryTrackAopConfiguration {
    private final TrackManager trackManager;

    public EasyQueryTrackAopConfiguration(TrackManager trackManager) {

        this.trackManager = trackManager;
    }

    @Around("execution(public * *(..)) && @within(com.easy.query.core.annotation.EasyQueryTrack)")
    public Object easyQueryTrack(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        EasyQueryTrack easyQueryTrack = method.getAnnotation(EasyQueryTrack.class); //通过反射拿到注解对象
        if (easyQueryTrack.enable()) {
            try {
                trackManager.begin();
                return pjp.proceed();
            } finally {
                trackManager.release();
            }

        } else {
            return pjp.proceed();
        }
    }
}
