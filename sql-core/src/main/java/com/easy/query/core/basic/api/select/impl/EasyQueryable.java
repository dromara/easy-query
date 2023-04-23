package com.easy.query.core.basic.api.select.impl;

import com.easy.query.core.basic.api.select.abstraction.AbstractQueryable1;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * @FileName: EasyQueryable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:28
 * @author xuejiaming
 */
public class EasyQueryable<T> extends AbstractQueryable1<T> {
    public EasyQueryable(Class<T> tClass, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(tClass, sqlEntityExpression);
    }
}
