package com.easy.query.core.basic.api.insert;

import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;

/**
 * @FileName: EasyInsertable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:45
 * @author xuejiaming
 */
public class EasyInsertable<T> extends AbstractInsertable<T> {
    public EasyInsertable(Class<T> clazz, EntityInsertExpressionBuilder entityInsertExpression) {
        super(clazz, entityInsertExpression);
    }
}
