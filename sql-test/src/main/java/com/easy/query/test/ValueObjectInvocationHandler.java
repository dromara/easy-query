package com.easy.query.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * create time 2023/11/6 10:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class ValueObjectInvocationHandler implements InvocationHandler {
    private final Object realObject;

    public ValueObjectInvocationHandler(Object realObject){

        this.realObject = realObject;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在方法调用前执行额外操作
        System.out.println("Before method call: " + method.getName());

        // 调用真实对象的方法
        Object result = method.invoke(realObject, args);

        // 在方法调用后执行额外操作
        System.out.println("After method call: " + method.getName());

        return result;
    }
}
