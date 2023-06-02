package com.easy.query.core.basic.api.select.impl;

import com.easy.query.core.basic.api.select.abstraction.AbstractObjectQueryable2;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * @author xuejiaming
 * @FileName: EasyQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:30
 */
public class EasyObjectQueryable2<T1, T2> extends AbstractObjectQueryable2<T1, T2> {
    public EasyObjectQueryable2(Class<T1> t1Class, Class<T2> t2Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, t2Class, sqlEntityExpression);
    }
}
