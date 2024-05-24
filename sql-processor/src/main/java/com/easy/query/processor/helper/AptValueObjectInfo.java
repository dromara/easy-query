package com.easy.query.processor.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/11/8 16:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class AptValueObjectInfo {
    private final String entityName;
    private final Map<String,AptPropertyInfo> propertieMap;
    private final List<AptValueObjectInfo> children;

    public AptValueObjectInfo(String entityName){

        this.entityName = entityName;
        this.propertieMap = new LinkedHashMap<>();
        this.children = new ArrayList<>();
    }

    /**
     * valueObject属性所属类名
     * @return
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * 这个类有哪些属性
     * @return
     */
    public List<AptPropertyInfo> getProperties() {
        return new ArrayList<>(propertieMap.values());
    }

    public void addProperties(AptPropertyInfo aptPropertyInfo){
        propertieMap.putIfAbsent(aptPropertyInfo.getPropertyName(),aptPropertyInfo);
    }

    /**
     * 嵌套valueObject
     * @return
     */
    public List<AptValueObjectInfo> getChildren() {
        return children;
    }

    public String toFileString() {
        return null;
    }
}
