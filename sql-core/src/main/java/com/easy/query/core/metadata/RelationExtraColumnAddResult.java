package com.easy.query.core.metadata;

/**
 * create time 2025/3/3 07:29
 * 文件说明
 *
 * @author xuejiaming
 */
public class RelationExtraColumnAddResult {
    public final RelationExtraColumn relationExtraColumn;
    public final String alias;

    public RelationExtraColumnAddResult(RelationExtraColumn relationExtraColumn, String alias){
        this.relationExtraColumn = relationExtraColumn;
        this.alias = alias;
    }
}
