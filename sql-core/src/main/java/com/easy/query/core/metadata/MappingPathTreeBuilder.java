package com.easy.query.core.metadata;

import java.util.function.Consumer;

/**
 * create time 2024/6/12 17:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class MappingPathTreeBuilder {
    public static void insertPath(MappingPathTreeNode root, String[] path, NavigateFlatMetadata navigateFlatMetadata, Consumer<String> checkInNavigateConsumer) {
        MappingPathTreeNode currentNode = root;

        for (String part : path) {
            checkInNavigateConsumer.accept(part);
            MappingPathTreeNode childNode = currentNode.findChild(part);
            if (childNode == null) {
                childNode = new MappingPathTreeNode(part);
                currentNode.addChild(childNode);
            }
            childNode.addNavigateFlatMetadata(navigateFlatMetadata);
            childNode.setDeep(currentNode.getDeep()+1);
            currentNode = childNode;
        }
    }
}
