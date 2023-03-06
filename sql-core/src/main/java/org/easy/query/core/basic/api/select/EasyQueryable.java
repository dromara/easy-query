package org.easy.query.core.basic.api.select;

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
    public String toSql() {
        return sqlEntityExpression.toSql();
    }
}
