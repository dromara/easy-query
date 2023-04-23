package com.easy.query.core.basic.api.select.impl;

import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.basic.api.select.abstraction.AbstractQueryable4;

/**
 * @FileName: EasyQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:30
 * @author xuejiaming
 */
public class EasyQueryable4<T1,T2,T3,T4> extends AbstractQueryable4<T1,T2,T3,T4> {
    public EasyQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class,Class<T4> t4Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, t2Class,t3Class,t4Class, sqlEntityExpression);
    }
}
