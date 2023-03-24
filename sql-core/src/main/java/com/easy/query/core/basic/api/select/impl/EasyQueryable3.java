package com.easy.query.core.basic.api.select.impl;

import com.easy.query.core.basic.api.select.abstraction.AbstractQueryable3;
import com.easy.query.core.query.SqlEntityQueryExpression;

/**
 * @FileName: EasyQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:30
 * @author xuejiaming
 */
public class EasyQueryable3<T1,T2,T3> extends AbstractQueryable3<T1,T2,T3> {
    public EasyQueryable3(Class<T1> t1Class, Class<T2> t2Class,Class<T3> t3Class, SqlEntityQueryExpression sqlEntityExpression) {
        super(t1Class, t2Class,t3Class, sqlEntityExpression);
    }

    @Override
    public String toInternalSql() {
        return sqlEntityExpression.toSql();
    }
}
