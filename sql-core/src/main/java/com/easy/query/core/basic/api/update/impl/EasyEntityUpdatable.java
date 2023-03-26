package com.easy.query.core.basic.api.update.impl;

import com.easy.query.core.basic.api.update.abstraction.AbstractEntityUpdatable;
import com.easy.query.core.expression.sql.SqlEntityUpdateExpression;

import java.util.Collection;

/**
 * @FileName: EasyEntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/3/6 12:20
 * @author xuejiaming
 */
public class EasyEntityUpdatable<T> extends AbstractEntityUpdatable<T> {

    public EasyEntityUpdatable(Collection<T> entities, SqlEntityUpdateExpression sqlEntityUpdateExpression) {
        super(entities, sqlEntityUpdateExpression);
    }

    @Override
    public String toSql(Object entity) {
        return sqlEntityUpdateExpression.toSql(entity);
    }
}
