package com.easy.query.core.basic.api.select.impl;

import com.easy.query.core.basic.api.select.abstraction.AbstractObjectQueryable3;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * @author xuejiaming
 * @FileName: EasyQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:30
 */
public class EasyObjectQueryable3<T1, T2, T3> extends AbstractObjectQueryable3<T1, T2, T3> {
    public EasyObjectQueryable3(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, t2Class, t3Class, sqlEntityExpression);
    }
}
