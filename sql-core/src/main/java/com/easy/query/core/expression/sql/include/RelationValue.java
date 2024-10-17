package com.easy.query.core.expression.sql.include;

import java.util.List;

/**
 * create time 2024/10/17 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RelationValue {
    boolean isNull();
    List<Object> getValues();
}
