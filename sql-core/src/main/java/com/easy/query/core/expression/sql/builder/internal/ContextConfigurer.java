package com.easy.query.core.expression.sql.builder.internal;

/**
 * create time 2024/7/4 08:40
 * 文上下文配置
 *
 * @author xuejiaming
 */
public interface ContextConfigurer {
    /**
     * 设置行为
     * @return
     */
    EasyBehavior getBehavior();

    /**
     * 设置groupSize
     * @param groupSize
     */
    void setGroupSize(Integer groupSize);

    /**
     * 获取当前groupSize
     * @return
     */
    Integer getGroupSize();


    void setResultSizeLimit(long resultSizeLimit);
    long getResultSizeLimit();



    Boolean getPrintSQL();
    void setPrintSQL(Boolean printSQL);
}
