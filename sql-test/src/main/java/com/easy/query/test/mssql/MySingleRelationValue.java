package com.easy.query.test.mssql;

import com.easy.query.core.expression.sql.include.RelationNullValueValidator;
import com.easy.query.core.expression.sql.include.SingleRelationValue;

/**
 * create time 2025/3/19 12:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySingleRelationValue extends SingleRelationValue {

    private final RelationNullValueValidator relationNullValueValidator;

    public MySingleRelationValue(Object value, RelationNullValueValidator relationNullValueValidator) {
        super(value, relationNullValueValidator);
        this.relationNullValueValidator = relationNullValueValidator;
    }
}
