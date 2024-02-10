package com.easy.query.core.expression.parser.core.available;

import com.easy.query.core.expression.RelationTableKey;

/**
 * create time 2024/2/10 14:37
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RelationTableAvailable extends TableAvailable{
    RelationTableKey getRelationTableKey();
}
