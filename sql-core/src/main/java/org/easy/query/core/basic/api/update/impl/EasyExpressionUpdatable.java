package org.easy.query.core.basic.api.update.impl;

import org.easy.query.core.basic.api.update.abstraction.AbstractExpressionUpdatable;
import org.easy.query.core.query.SqlEntityUpdateExpression;

/**
 * @FileName: EasyExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/3/6 12:21
 * @Created by xuejiaming
 */
public class EasyExpressionUpdatable<T> extends AbstractExpressionUpdatable<T> {
    public EasyExpressionUpdatable(Class<T> clazz, SqlEntityUpdateExpression sqlEntityUpdateExpression) {
        super(clazz, sqlEntityUpdateExpression);
    }

    @Override
    public String toSql() {
        return sqlEntityUpdateExpression.toSql();
    }
}
