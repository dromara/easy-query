package com.easy.query.core.util;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEOption;
import com.easy.query.core.expression.sql.include.EasyTreeNodeDown;
import com.easy.query.core.expression.sql.include.EasyTreeNodeUp;
import com.easy.query.core.expression.sql.include.MultiRelationValue;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.expression.sql.include.EasyTreeNode;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadata;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadataFactory;
import com.easy.query.core.expression.sql.include.relation.RelationValueFactory;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.metadata.TreeDeepItem;
import com.easy.query.core.metadata.TreeSelfTargetItem;
import com.easy.query.core.util.common.ListTreeDeepItemAvailable;
import com.easy.query.core.util.common.PropertyTreeDeepItemAvailable;
import com.easy.query.core.util.common.TreeDeepItemAvailable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * create time 2024/11/13 22:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyTreeUtil {

    /**
     * 根据所有树节点列表，生成含有所有树形结构的列表
     *
     * @param nodes 树形节点列表
     * @param <T>   节点类型
     * @return 树形结构列表
     */
    public static <T> List<T> generateTrees(List<T> nodes, EntityMetadata entityMetadata, NavigateMetadata navigateMetadata, TreeSelfTargetItem treeSelfTargetItem , QueryRuntimeContext runtimeContext, TreeCTEOption treeCTEOption, List<TreeDeepItem> deepItems) {

        RelationValueColumnMetadataFactory relationValueColumnMetadataFactory = runtimeContext.getRelationValueColumnMetadataFactory();
        RelationValueColumnMetadata selfRelationValueColumnMetadata = relationValueColumnMetadataFactory.create(entityMetadata, treeSelfTargetItem.selfProperties);
        RelationValueColumnMetadata targetRelationValueColumnMetadata = relationValueColumnMetadataFactory.create(entityMetadata, treeSelfTargetItem.targetProperties);
        TreeDeepItemAvailable treeDeepItemAvailable = getTreeDeepItemAvailable(entityMetadata, treeCTEOption, deepItems);

        List<EasyTreeNode<T>> roots = new ArrayList<>();
        List<EasyTreeNode<T>> easyTreeNodes = new ArrayList<>();
        if (treeCTEOption.isUp()) {
            flatNodeToTreeNodeUp(nodes, roots, easyTreeNodes, selfRelationValueColumnMetadata, targetRelationValueColumnMetadata, navigateMetadata, treeDeepItemAvailable);
        } else {
            flatNodeToTreeNodeDown(nodes, roots, easyTreeNodes, selfRelationValueColumnMetadata, targetRelationValueColumnMetadata, navigateMetadata, treeDeepItemAvailable);
        }

        roots.forEach(r -> {
            setChildren(r, easyTreeNodes);
        });
        return roots.stream().map(EasyTreeNode::getEntity).collect(Collectors.toList());
    }

    private static <T> void flatNodeToTreeNodeUp(List<T> nodes,
                                                 List<EasyTreeNode<T>> roots,
                                                 List<EasyTreeNode<T>> easyTreeNodes,
                                                 RelationValueColumnMetadata selfRelationValueColumnMetadata,
                                                 RelationValueColumnMetadata targetRelationValueColumnMetadata,
                                                 NavigateMetadata navigateMetadata,
                                                 TreeDeepItemAvailable treeDeepItemAvailable) {

        Map<RelationValue, T> targetMap = new HashMap<>();
        int i = 0;
        for (T node : nodes) {
            RelationValue selfValue = selfRelationValueColumnMetadata.getRelationValue(node);
            RelationValue targetValue = targetRelationValueColumnMetadata.getRelationValue(node);
            long deep = treeDeepItemAvailable.getDeep(node, i);
            EasyTreeNodeUp<T> tEasyTreeNodeUp = new EasyTreeNodeUp<>(node, selfValue, targetValue, navigateMetadata, deep);
            targetMap.put(tEasyTreeNodeUp.getSelf(), node);
            easyTreeNodes.add(tEasyTreeNodeUp);
            i++;
        }
        Iterator<EasyTreeNode<T>> nodeIterator = easyTreeNodes.iterator();
        while (nodeIterator.hasNext()){
            EasyTreeNode<T> node = nodeIterator.next();
            boolean root = !targetMap.containsKey(node.getTarget());
            if (root) {
                roots.add(node);
                // 从所有节点列表中删除该节点，以免后续重复遍历该节点
                nodeIterator.remove();
            }
        }
    }

    private static <T> void flatNodeToTreeNodeDown(List<T> nodes,
                                                   List<EasyTreeNode<T>> roots,
                                                   List<EasyTreeNode<T>> easyTreeNodes,
                                                   RelationValueColumnMetadata selfRelationValueColumnMetadata,
                                                   RelationValueColumnMetadata targetRelationValueColumnMetadata,
                                                   NavigateMetadata navigateMetadata,
                                                   TreeDeepItemAvailable treeDeepItemAvailable) {

        int i = 0;
        for (T node : nodes) {
            RelationValue selfValue = selfRelationValueColumnMetadata.getRelationValue(node);
            RelationValue targetValue = targetRelationValueColumnMetadata.getRelationValue(node);
            long deep = treeDeepItemAvailable.getDeep(node, i);
            EasyTreeNode<T> easyTreeNode = new EasyTreeNodeDown<>(node, selfValue, targetValue, navigateMetadata, deep);
            if (deep == 0) {
                roots.add(easyTreeNode);
            } else {
                easyTreeNodes.add(easyTreeNode);
            }
            i++;
        }
    }

    private static TreeDeepItemAvailable getTreeDeepItemAvailable(EntityMetadata entityMetadata, TreeCTEOption treeCTEOption, List<TreeDeepItem> deepItems) {
        if (EasyCollectionUtil.isEmpty(deepItems)) {
            String propertyNameOrNull = entityMetadata.getPropertyNameOrNull(treeCTEOption.getDeepColumnName());
            if (propertyNameOrNull == null) {
                throw new EasyQueryInvalidOperationException("Unable to obtain depth information, so the corresponding tree structure cannot be constructed. deep column:[" + treeCTEOption.getDeepColumnName() + "]");
            }
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyNameOrNull);
            return new PropertyTreeDeepItemAvailable(columnMetadata);
        }
        return new ListTreeDeepItemAvailable(deepItems);
    }


    /**
     * 从所有节点列表中查找并设置parent的所有子节点
     *
     * @param parent 父节点
     * @param nodes  所有树节点列表
     */
    @SuppressWarnings("all")
    public static <T> void setChildren(EasyTreeNode<T> parent, List<EasyTreeNode<T>> nodes) {
        List<EasyTreeNode<T>> children = new ArrayList<>();
        RelationValue parentId = parent.getSelf();
        for (Iterator<EasyTreeNode<T>> ite = nodes.iterator(); ite.hasNext(); ) {
            EasyTreeNode<T> node = ite.next();
            if (Objects.equals(node.getTarget(), parentId)) {
                children.add(node);
                // 从所有节点列表中删除该节点，以免后续重复遍历该节点
                ite.remove();
            }
        }
        // 如果孩子为空，则直接返回,否则继续递归设置孩子的孩子
        if (children.isEmpty()) {
            return;
        }
        parent.setChildren(children);
        children.forEach(m -> {
            // 递归设置子节点
            setChildren(m, nodes);
        });
    }
}
