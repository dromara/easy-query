package com.easy.query.core.expression;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.Objects;

/**
 * create time 2024/2/10 17:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RelationTableKey {
    TableAvailable getTable();
}

