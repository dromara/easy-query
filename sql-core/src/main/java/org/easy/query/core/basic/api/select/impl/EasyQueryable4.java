package org.easy.query.core.basic.api.select.impl;

import org.easy.query.core.basic.api.select.abstraction.AbstractQueryable3;
import org.easy.query.core.basic.api.select.abstraction.AbstractQueryable4;
import org.easy.query.core.query.SqlEntityQueryExpression;

/**
 * @FileName: EasyQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:30
 * @Created by xuejiaming
 */
public class EasyQueryable4<T1,T2,T3,T4> extends AbstractQueryable4<T1,T2,T3,T4> {
    public EasyQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class,Class<T4> t4Class, SqlEntityQueryExpression sqlEntityExpression) {
        super(t1Class, t2Class,t3Class,t4Class, sqlEntityExpression);
    }

    @Override
    public String toInternalSql() {
        return sqlEntityExpression.toSql();
    }
}
