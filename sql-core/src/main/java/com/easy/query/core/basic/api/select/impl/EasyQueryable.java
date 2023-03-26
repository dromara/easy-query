package com.easy.query.core.basic.api.select.impl;

import com.easy.query.core.basic.api.select.abstraction.AbstractQueryable1;
import com.easy.query.core.expression.sql.SqlEntityQueryExpression;

/**
 * @FileName: EasyQueryable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:28
 * @author xuejiaming
 */
public class EasyQueryable<T> extends AbstractQueryable1<T> {
    public EasyQueryable(Class<T> tClass, SqlEntityQueryExpression sqlEntityExpression) {
        super(tClass, sqlEntityExpression);
    }

    @Override
    public String toInternalSql() {
        return sqlEntityExpression.toSql();
    }
}
