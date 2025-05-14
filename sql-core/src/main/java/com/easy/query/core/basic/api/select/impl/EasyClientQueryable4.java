package com.easy.query.core.basic.api.select.impl;

import com.easy.query.core.basic.api.select.abstraction.AbstractClientQueryable4;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * @author xuejiaming
 * @FileName: EasyQueryable2.java
 * @Description: 文件说明
 * create time 2023/3/6 08:30
 */
public class EasyClientQueryable4<T1, T2, T3, T4> extends AbstractClientQueryable4<T1, T2, T3, T4> {
    public EasyClientQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, t2Class, t3Class, t4Class, sqlEntityExpression);
    }
}
