package com.easy.query.core.expression.sql.builder.internal;

import java.util.function.Function;

/**
 * create time 2024/7/4 08:40
 * 文上下文配置
 *
 * @author xuejiaming
 */
public interface ContextConfigurer {
    /**
     * 设置行为
     *
     * @return
     */
    EasyBehavior getBehavior();

    /**
     * 设置groupSize
     *
     * @param groupSize
     */
    void setGroupSize(Integer groupSize);

    /**
     * 获取当前groupSize
     *
     * @return
     */
    Integer getGroupSize();


    void setResultSizeLimit(long resultSizeLimit);

    long getResultSizeLimit();


    /**
     * @return null表示不设置
     */
    Boolean getPrintSQL();

    /**
     * 是否打印sql优先级最高
     *
     * @param printSQL null表示不设置
     */
    void setPrintSQL(Boolean printSQL);

    void setPrintNavSQL(Boolean printSQL);

    Boolean getPrintNavSQL();

    void setConfigureArgument(Object arg);

    Object getConfigureArgument();

    void setReverseOrder(boolean reverseOrder);

}
