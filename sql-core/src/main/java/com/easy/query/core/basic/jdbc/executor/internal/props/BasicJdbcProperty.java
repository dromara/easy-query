package com.easy.query.core.basic.jdbc.executor.internal.props;

/**
 * create time 2023/8/12 14:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class BasicJdbcProperty implements JdbcProperty {
    private final int jdbcIndex;
    private final Class<?> propertyType;

    public BasicJdbcProperty(int index, Class<?> propertyType){

        this.jdbcIndex = index+1;
        this.propertyType = propertyType;
    }
    @Override
    public int getJdbcIndex() {
        return jdbcIndex;
    }

    @Override
    public Class<?> getPropertyType() {
        return propertyType;
    }

    @Override
    public boolean isPrimitive() {
        return propertyType.isPrimitive();
    }
}
