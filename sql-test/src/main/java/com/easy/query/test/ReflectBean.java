package com.easy.query.test;

import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.common.bean.FastBeanProperty;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.util.EasyClassUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * create time 2024/6/7 09:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class ReflectBean implements FastBean {
    private final Class<?> entityClass;

    public ReflectBean(Class<?> entityClass) {

        this.entityClass = entityClass;
    }

    @Override
    public Property<Object, ?> getBeanGetter(FastBeanProperty prop) {
        return getReflectGetProperty(prop);
    }

    public Property<Object, ?> getReflectGetProperty(FastBeanProperty prop) {
        Method readMethod = prop.getReadMethod();

        return bean -> {
            try {
                return readMethod.invoke(bean);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new EasyQueryException(e);
            }
        };
    }

    @Override
    public PropertySetterCaller<Object> getBeanSetter(FastBeanProperty prop) {
        return getReflectSetProperty(prop);
    }

    public PropertySetterCaller<Object> getReflectSetProperty(FastBeanProperty prop) {
        Method setter = EasyClassUtil.getWriteMethodOrNull(prop, entityClass);
        if (setter == null) {
            return (bean, value) -> {
            };
        }
        return (bean, value) -> {
            try {
                setter.invoke(bean, value);
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                throw new EasyQueryException("Cannot set " + prop.getName() + ": " + e.getMessage());
            }
        };
    }

    @Override
    public Supplier<Object> getBeanConstructorCreator() {
        return () -> {
            return EasyClassUtil.newInstance(entityClass);
        };
    }
}
