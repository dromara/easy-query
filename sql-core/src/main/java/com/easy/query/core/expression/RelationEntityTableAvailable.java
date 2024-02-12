package com.easy.query.core.expression;

import com.easy.query.core.expression.parser.core.available.RelationTableAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2024/2/10 14:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class RelationEntityTableAvailable extends EntityTableAvailable implements RelationTableAvailable {
    private final RelationTableKey relationTableKey;
    private final TableAvailable originalTable;

    public RelationEntityTableAvailable(RelationTableKey relationTableKey, TableAvailable originalTable, EntityMetadata entityMetadata, boolean isAnonymous) {
        super(entityMetadata, isAnonymous);
        this.relationTableKey = relationTableKey;
        this.originalTable = originalTable;
    }

    @Override
    public RelationTableKey getRelationTableKey() {
        return relationTableKey;
    }

    @Override
    public TableAvailable getOriginalTable() {
        return originalTable;
    }
}
