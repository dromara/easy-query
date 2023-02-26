package org.easy.query.core.basic.jdbc.parameter;

/**
 * @FileName: DbParameter.java
 * @Description: 数据库参数封装
 * @Date: 2023/2/26 10:50
 * @Created by xuejiaming
 */
public final class DbParameter {
    private final Class<?> propertyType;
    private final Object Value;
    public DbParameter(Class<?> propertyType, Object value) {
        this.propertyType = propertyType;
        Value = value;
    }

    /**
     * 获取对应值
     * @return
     */

    public Class<?> getPropertyType() {
        return propertyType;
    }

    public Object getValue() {
        return Value;
    }
}
