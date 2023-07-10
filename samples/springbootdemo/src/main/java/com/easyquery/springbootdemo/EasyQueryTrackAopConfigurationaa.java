//package com.easyquery.springbootdemo;
//
//import com.easy.query.core.annotation.EasyQueryTrack;
//import com.easy.query.core.api.client.EasyQueryClient;
//import com.easy.query.core.basic.extension.track.TrackManager;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//import java.lang.reflect.Method;
//
///**
// * @author xuejiaming
// * @FileName: EasyQueryTrackAopConfiguration.java
// * @Description: 文件说明
// * @Date: 2023/3/20 20:58
// */
//@Aspect
//@Configuration
//public class EasyQueryTrackAopConfigurationaa {
//    @Autowired
//    private EasyQueryClient easyQueryClient;
//
//    @Around("execution(public * *(..)) && @annotation(com.easy.query.core.annotation.EasyQueryTrack)")
//    public Object easyQueryTrack(ProceedingJoinPoint pjp) throws Throwable {
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        Method method = signature.getMethod();
//        EasyQueryTrack easyQueryTrack = method.getAnnotation(EasyQueryTrack.class); //通过反射拿到注解对象
//        if (easyQueryTrack.enable()) {
//            TrackManager trackManager = easyQueryClient.getRuntimeContext().getTrackManager();
//            try {
//                trackManager.begin();
//                return pjp.proceed();
//            } finally {
//                trackManager.release();
//            }
//
//        } else {
//            return pjp.proceed();
//        }
//    }
//}
