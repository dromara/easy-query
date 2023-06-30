package com.easy.query.core.basic.jdbc.executor.impl.proxy;

import com.easy.query.core.basic.jdbc.executor.impl.def.EntityResultColumnMetadata;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.proxy.BeanProxy;

/**
 * create time 2023/6/30 22:53
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyEntityResultColumnMetadata extends EntityResultColumnMetadata {
    private final BeanProxy beanProxy;

    public ProxyEntityResultColumnMetadata(BeanProxy beanProxy, ColumnMetadata columnMetadata) {
        super(columnMetadata);
        this.beanProxy = beanProxy;
    }
    @Override
    public void setValue(Object bean, Object value) {
        beanProxy.setValue(bean,getPropertyName(),value);
    }

    @Override
    public Object getValue(Object bean) {
        return beanProxy.getValue(bean,getPropertyName());
    }
}
