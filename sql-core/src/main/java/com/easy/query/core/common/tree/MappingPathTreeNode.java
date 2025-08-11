package com.easy.query.core.common.tree;

import com.easy.query.core.metadata.NavigateFlatMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2024/6/12 17:51
 * 文件说明
 *
 * @author xuejiaming
 */
public class MappingPathTreeNode {
    private String name;
    private List<NavigateFlatMetadata> navigateFlatMetadataList;
    private List<MappingPathTreeNode> children;
    private int flatBasicCount;
    private int deep=0;

    public MappingPathTreeNode(String name) {
        this.name = name;
        this.navigateFlatMetadataList =  new ArrayList<>();
        this.children = new ArrayList<>();
    }

    // 查找子节点
    public MappingPathTreeNode findChild(String name) {
        for (MappingPathTreeNode child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        return null;
    }

    // 添加子节点
    public void addChild(MappingPathTreeNode child) {
        children.add(child);
    }

    // 打印树（用于调试）
    public void printTree(String prefix) {
        System.out.println(prefix + name);
        for (MappingPathTreeNode child : children) {
            child.printTree(prefix + "--");
        }
    }

    public List<MappingPathTreeNode> getChildren() {
        return children;
    }

    public boolean hasChildren() {
        return EasyCollectionUtil.isNotEmpty(children);
    }

    public String getName() {
        return name;
    }

    public List<NavigateFlatMetadata> getNavigateFlatMetadataList() {
        return navigateFlatMetadataList;
    }
    public void addNavigateFlatMetadata(NavigateFlatMetadata navigateFlatMetadata) {
        if(navigateFlatMetadata.isBasicType()){
            flatBasicCount++;
        }
        navigateFlatMetadataList.add(navigateFlatMetadata);
    }

    public boolean anyBasicType(){
        return flatBasicCount>0;
    }
    public boolean allBasicType(int size){
        return flatBasicCount==size;
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }
}
