package com.easy.query.core.metadata;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * create time 2025/6/28 08:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class IncludePathTreeNode {
    private String name;
    private IncludeFunction includeFunction;
    private List<IncludePathTreeNode> children;
    private int deep = 0;

    public IncludePathTreeNode(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    // 查找子节点
    public IncludePathTreeNode findChild(String name) {
        for (IncludePathTreeNode child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        return null;
    }

    // 添加子节点
    public void addChild(IncludePathTreeNode child) {
        children.add(child);
    }

    // 打印树（用于调试）
    public void printTree(String prefix) {
        System.out.println(prefix + name);
        for (IncludePathTreeNode child : children) {
            child.printTree(prefix + "--");
        }
    }

    public List<IncludePathTreeNode> getChildren() {
        return children;
    }

    public boolean hasChildren() {
        return EasyCollectionUtil.isNotEmpty(children);
    }

    public String getName() {
        return name;
    }



    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }

    public IncludeFunction getIncludeFunction() {
        return includeFunction;
    }

    public void setIncludeFunction(IncludeFunction includeFunction) {
        this.includeFunction = includeFunction;
    }

    public static class IncludeFunction {
        public final List<Function<ClientQueryable<?>, ClientQueryable<?>>> functions;

        public IncludeFunction(List<Function<ClientQueryable<?>, ClientQueryable<?>>> functions) {
            this.functions = functions;
        }
    }
}
