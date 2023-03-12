package com.easy.query.core.bean;

import com.easy.query.core.exception.EasyQueryException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @FileName: BeanMethodInvoker.java
 * @Description: 文件说明
 * @Date: 2023/2/23 21:25
 * @Created by xuejiaming
 */
public class BeanMethodInvoker implements MethodInvoker{
    private final PropertyDescriptor pd;
    private final Method readMethod;

    public BeanMethodInvoker(PropertyDescriptor pd){

        this.pd = pd;
        this.readMethod = pd.getReadMethod();
        this.readMethod.setAccessible(true);
    }
    @Override
    public Object get(Object obj) {
        try {

            return readMethod.invoke(obj, null);
        } catch (IllegalArgumentException e) {
            throw new EasyQueryException( "错误参数", e);

        } catch (IllegalAccessException e) {
            throw new EasyQueryException("无法访问", e);

        } catch (InvocationTargetException e) {
            Throwable target = e.getTargetException();
            if (target instanceof EasyQueryException) {
                throw (EasyQueryException) target;
            }
            throw new EasyQueryException("属性访问异常", e.getTargetException());
        }
    }

    @Override
    public Class<?> getReturnType() {
        return pd.getPropertyType();
    }

    @Override
    public Method getMethod() {
        return pd.getReadMethod();
    }

    @Override
    public void set(Object ins, Object value) {
        try {
            Method method = pd.getWriteMethod();
            if(method==null){
                throw new EasyQueryException("找不到相应的set方法，确保方法符合JavaBean规范 " + pd);
            }
            method.invoke(ins, value);
        } catch (IllegalAccessException e) {
            throw new EasyQueryException("无法访问 " + pd, e);
        } catch (IllegalArgumentException e) {
            throw new EasyQueryException( "错误参数 " + pd, e);
        } catch (InvocationTargetException e) {
            Throwable target = e.getTargetException();
            if (target instanceof EasyQueryException) {
                throw (EasyQueryException) target;
            }
            throw new EasyQueryException("属性访问异常 " + pd, e.getTargetException());
        }
    }
}
