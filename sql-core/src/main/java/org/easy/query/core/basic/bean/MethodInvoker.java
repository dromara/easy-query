package org.easy.query.core.basic.bean;

import java.lang.reflect.Method;

/**
 * @FileName: MethodInvoker.java
 * @Description: 文件说明
 * @Date: 2023/2/23 21:25
 * @Created by xuejiaming
 */
public interface MethodInvoker {
    /**
     * @param obj 传入的对象
     * @return 返回值
     */
    Object get(Object obj);

    /**
     * @return 返回结果
     */
    Class<?> getReturnType();

    Method getMethod();

    void set(Object ins, Object value);
}
