package com.easy.query.core.expression.sql.include;

import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2024/11/14 00:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyTreeNodeWithoutDeep<T> implements EasyTreeNode<T> {
    private final T entity;
    private final RelationValue self;
    private final RelationValue target;
    private final NavigateMetadata navigateMetadata;

    public EasyTreeNodeWithoutDeep(T entity, RelationValue self, RelationValue target, NavigateMetadata navigateMetadata) {
        this.entity = entity;
        this.self = self;
        this.target = target;
        this.navigateMetadata = navigateMetadata;
        Object children = navigateMetadata.getGetter().apply(entity);
        if (children == null) {
            navigateMetadata.getSetter().call(entity, EasyCollectionUtil.emptyList());
        }
    }

    @Override
    public T getEntity() {
        return entity;
    }

    @Override
    public RelationValue getSelf() {
        return self;
    }

    @Override
    public RelationValue getTarget() {
        return target;
    }

    @Override
    public long getDeep() {
        return -1;
    }

    @Override
    public void setChildren(List<EasyTreeNode<T>> children) {
        List<T> select = EasyCollectionUtil.select(children, (e, i) -> e.getEntity());
        navigateMetadata.getSetter().call(entity, select);
    }
}
