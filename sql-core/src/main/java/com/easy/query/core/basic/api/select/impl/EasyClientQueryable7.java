package com.easy.query.core.basic.api.select.impl;

import com.easy.query.core.basic.api.select.abstraction.AbstractClientQueryable7;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * @author xuejiaming
 * @FileName: EasyQueryable2.java
 * @Description: 文件说明
 * create time 2023/3/6 08:30
 */
public class EasyClientQueryable7<T1, T2, T3, T4, T5, T6, T7> extends AbstractClientQueryable7<T1, T2, T3, T4, T5, T6, T7> {
    public EasyClientQueryable7(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class,Class<T7> t7Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, t2Class, t3Class, t4Class, t5Class, t6Class, t7Class, sqlEntityExpression);
    }
}
