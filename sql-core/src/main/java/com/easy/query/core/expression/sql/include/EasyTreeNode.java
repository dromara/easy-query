package com.easy.query.core.expression.sql.include;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2024/11/14 00:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyTreeNode<T> {
    private final T entity;
    private final RelationValue self;
    private final RelationValue target;
    private final NavigateMetadata navigateMetadata;

    public EasyTreeNode(T entity, RelationValue self, RelationValue target,  NavigateMetadata navigateMetadata) {
        this.entity = entity;
        this.self = self;
        this.target = target;
        this.navigateMetadata = navigateMetadata;
        Object children = navigateMetadata.getGetter().apply(entity);
        if(children==null){
            navigateMetadata.getSetter().call(entity, EasyCollectionUtil.emptyList());
        }
    }

    public T getEntity() {
        return entity;
    }

    public RelationValue getSelf() {
        return self;
    }

    public RelationValue getTarget() {
        return target;
    }

    public void setChildren(List<EasyTreeNode<T>> children) {
        List<T> select = EasyCollectionUtil.select(children, (e, i) -> e.getEntity());
        navigateMetadata.getSetter().call(entity, select);
    }
}
