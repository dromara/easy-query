package com.easy.query.core.expression.sql.include;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2024/11/14 00:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyTreeNode<T> {

    T getEntity();

    RelationValue getSelf();

    RelationValue getTarget();

    long getDeep();

    void setChildren(List<EasyTreeNode<T>> children);
}
