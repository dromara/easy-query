package com.easy.query.core.expression.include.getter;

/**
 * create time 2025/5/7 19:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityIndex<TEntity> {
    public final TEntity entity;
    public final Integer index;
    public EntityIndex(TEntity entity,Integer index){
        this.entity = entity;
        this.index = index;
    }
}
