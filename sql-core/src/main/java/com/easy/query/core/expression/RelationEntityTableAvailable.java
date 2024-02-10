package com.easy.query.core.expression;

import com.easy.query.core.expression.parser.core.available.RelationTableAvailable;
import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2024/2/10 14:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class RelationEntityTableAvailable extends EntityTableAvailable implements RelationTableAvailable {
    private final RelationTableKey relationTableKey;

    public RelationEntityTableAvailable(RelationTableKey relationTableKey,EntityMetadata entityMetadata, boolean isAnonymous) {
        super(entityMetadata, isAnonymous);
        this.relationTableKey = relationTableKey;
    }

    @Override
    public RelationTableKey getRelationTableKey() {
        return relationTableKey;
    }
}
