package com.easy.query.core.bean.reflect;

import com.easy.query.core.bean.BeanCaller;
import com.easy.query.core.bean.BeanValueCaller;

/**
 * create time 2023/6/12 20:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class ReflectBeanValueCaller implements BeanValueCaller {
    @Override
    public BeanCaller getBeanCaller(Class<?> beanClass) {
        return new ReflectBeanCaller(beanClass);
    }
}
