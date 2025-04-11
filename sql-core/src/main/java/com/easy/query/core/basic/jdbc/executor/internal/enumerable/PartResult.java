package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

/**
 * create time 2024/8/5 11:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PartResult<TEntity> {
    public static final String PART_PREFIX ="__part__";
    TEntity getEntity();
    void setEntity(TEntity entity);
}
