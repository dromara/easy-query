//package com.easy.query.core.bean.lambda;
//
//import com.easy.query.core.bean.BeanCaller;
//import com.easy.query.core.bean.BeanValueCaller;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * create time 2023/6/12 20:32
// * 文件说明
// *
// * @author xuejiaming
// */
//public class LambdaBeanValueCaller implements BeanValueCaller {
//    private static final Map<Class<?>, BeanCaller> CLASS_FAST_BEAN_CACHE = new ConcurrentHashMap<>();
//
//    @Override
//    public BeanCaller getBeanCaller(Class<?> beanClass) {
//        return CLASS_FAST_BEAN_CACHE.computeIfAbsent(beanClass,k->new LambdaBeanCaller(beanClass));
//    }
//}
