package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

/**
 * create time 2023/12/18 22:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Draft {
    int capacity();
    void setValues(int index, Object value);
//    void setPropTypes(int index, Class<?> propType);
//    Class<?> getPropType(int index);
//
    /**
     * 判断是第几列是否需要被读取,index从0开始
     * @param index
     * @return
     */
    boolean readColumn(int index);
}
