package com.easy.query.core.basic.api.insert;

import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;

/**
 * @author xuejiaming
 * @FileName: EasyInsertable.java
 * @Description: 文件说明
 * create time 2023/3/6 08:45
 */
public class EasyClientInsertable<T> extends AbstractClientInsertable<T> {
    public EasyClientInsertable(Class<T> clazz, EntityInsertExpressionBuilder entityInsertExpression) {
        super(clazz, entityInsertExpression);
    }
}
