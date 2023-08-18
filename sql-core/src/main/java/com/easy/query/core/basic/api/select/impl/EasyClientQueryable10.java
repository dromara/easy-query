package com.easy.query.core.basic.api.select.impl;

import com.easy.query.core.basic.api.select.abstraction.AbstractClientQueryable10;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * @author xuejiaming
 * @FileName: EasyQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:30
 */
public class EasyClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends AbstractClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {
    public EasyClientQueryable10(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, Class<T8> t8Class, Class<T9> t9Class, Class<T10> t10Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, t2Class, t3Class, t4Class, t5Class, t6Class, t7Class, t8Class, t9Class, t10Class, sqlEntityExpression);
    }
}
