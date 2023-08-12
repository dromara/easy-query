package com.easy.query.core.basic.jdbc.executor;

/**
 * create time 2023/8/12 13:58
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataReader {
//    int getIndex();
    int getJdbcIndex();
    Class<?> getPropertyType();
    boolean isPrimitive();
}
