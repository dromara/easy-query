package com.easy.query.core.basic.jdbc.parameter;

/**
 * @Description: 文件说明
 * create time 2023/2/28 21:53
 * @author xuejiaming
 */
public interface BeanSQLParameter extends SQLParameter{
    void setBean(Object bean);
    boolean hasBean();
}
