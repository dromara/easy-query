package com.easy.query.core.metadata;

import java.util.function.Consumer;

/**
 * create time 2024/6/12 17:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class PathTreeBuilder {
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
            childNode.setDeep(currentNode.getDeep() + 1);
            currentNode = childNode;
        }
    }

    public static void insertPath(IncludePathTreeNode root, String[] paths, IncludePathTreeNode.IncludeFunction includeFunction) {
        IncludePathTreeNode currentNode = root;

        for (int i = 0; i < paths.length; i++) {
            String path = paths[i];
            IncludePathTreeNode childNode = currentNode.findChild(path);
            if (childNode == null) {
                childNode = new IncludePathTreeNode(path);
                currentNode.addChild(childNode);
                childNode.setDeep(currentNode.getDeep() + 1);
            }
            if (i == paths.length - 1) {
                if (childNode.getIncludeFunction() == null) {
                    childNode.setIncludeFunction(includeFunction);
                } else {
                    childNode.getIncludeFunction().functions.addAll(includeFunction.functions);
                }
            }
            currentNode = childNode;
        }
    }

}
