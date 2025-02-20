package com.easy.query.core.util;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.expression.sql.include.EasyTreeNode;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadata;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadataFactory;
import com.easy.query.core.expression.sql.include.relation.RelationValueFactory;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;

import java.util.ArrayList;
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
    public static <T> List<T> generateTrees(List<T> nodes, EntityMetadata entityMetadata, NavigateMetadata navigateMetadata, QueryRuntimeContext runtimeContext) {

        RelationValueColumnMetadataFactory relationValueColumnMetadataFactory = runtimeContext.getRelationValueColumnMetadataFactory();
        RelationValueColumnMetadata selfRelationValueColumnMetadata = relationValueColumnMetadataFactory.create(entityMetadata, navigateMetadata.getSelfPropertiesOrPrimary());
        RelationValueColumnMetadata targetRelationValueColumnMetadata = relationValueColumnMetadataFactory.create(entityMetadata, navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext));
//        relationValueColumnMetadata.getRelationValue()
        Map<RelationValue, T> targetMap = new HashMap<>();
        ArrayList<EasyTreeNode<T>> easyTreeNodes = new ArrayList<>();
        for (T node : nodes) {
            RelationValue selfValue = selfRelationValueColumnMetadata.getRelationValue(node);
            RelationValue targetValue = targetRelationValueColumnMetadata.getRelationValue(node);
            targetMap.put(selfValue,node);
            easyTreeNodes.add(new EasyTreeNode<>(node,selfValue,targetValue,navigateMetadata));
        }
//        Map<RelationValue, T> treeMap = new TreeMap<>();
        List<EasyTreeNode<T>> roots = new ArrayList<>();
        for (Iterator<EasyTreeNode<T>> ite = easyTreeNodes.iterator(); ite.hasNext(); ) {
            EasyTreeNode<T> node = ite.next();
            boolean root = !targetMap.containsKey(node.getTarget());
            if (root) {
                roots.add(node);
                // 从所有节点列表中删除该节点，以免后续重复遍历该节点
                ite.remove();
            }
        }

        roots.forEach(r -> {
            setChildren(r, easyTreeNodes);
        });
        return roots.stream().map(EasyTreeNode::getEntity).collect(Collectors.toList());
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
