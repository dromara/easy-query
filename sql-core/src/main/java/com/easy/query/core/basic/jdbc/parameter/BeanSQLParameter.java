package com.easy.query.core.basic.jdbc.parameter;

/**
 * @FileName: BeanSqlParameter.java
 * @Description: 文件说明
 * @Date: 2023/2/28 21:53
 * @author xuejiaming
 */
public interface BeanSQLParameter extends SQLParameter{
    void setBean(Object bean);
}
