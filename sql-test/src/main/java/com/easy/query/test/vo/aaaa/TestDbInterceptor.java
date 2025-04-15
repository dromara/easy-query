//package com.easy.query.test.vo.aaaa;
//
//import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
//import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * create time 2025/4/15 14:44
// * 文件说明
// *
// * @author xuejiaming
// */
//public class TestDbInterceptor {
//    @Autowired
//    private DbInterceptorHolder dbInterceptorHolder;
//    public void test(){
//        EasyQueryBootstrapper.defaultBuilderConfiguration()
//                .replaceService(DbInterceptorHolder.class, dbInterceptorHolder)
//                .replaceService(EntityExpressionExecutor.class, RealEntityExpressionExecutor.class)
//    }
//}
