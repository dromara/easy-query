package com.easy.query.core.expression.sql.builder.internal;

import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

/**
 * create time 2024/2/23 10:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class RelationEntityTableExpressionBuilderWrapper {
    private final EntityTableExpressionBuilder entityTableExpressionBuilder;
    /**
     * 使用次数
     */
    private int usedCount = 0;

    public RelationEntityTableExpressionBuilderWrapper(EntityTableExpressionBuilder entityTableExpressionBuilder){

        this.entityTableExpressionBuilder = entityTableExpressionBuilder;
    }

    public EntityTableExpressionBuilder getEntityTableExpressionBuilder() {
        return entityTableExpressionBuilder;
    }

    public int getUsedCount() {
        return usedCount;
    }
    public void increment(){
        usedCount++;
    }
}
