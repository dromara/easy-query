package org.easy.query.core.basic.api.select.impl;

import org.easy.query.core.basic.api.select.abstraction.AbstractQueryable1;
import org.easy.query.core.query.SqlEntityQueryExpression;

/**
 * @FileName: EasyQueryable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:28
 * @Created by xuejiaming
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
