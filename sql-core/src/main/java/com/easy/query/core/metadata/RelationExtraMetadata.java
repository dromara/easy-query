package com.easy.query.core.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2024/4/13 09:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class RelationExtraMetadata {
    private final Map<String, RelationExtraColumn> relationExtraColumnMap;
    private final List<Map<String,Object>> relationExtraColumnList;
    private int currentIndex;

    public RelationExtraMetadata(){

        this.relationExtraColumnMap = new HashMap<>();
        this.relationExtraColumnList = new ArrayList<>();
        this.currentIndex = -1;
    }

    public Map<String, RelationExtraColumn> getRelationExtraColumnMap() {
        return relationExtraColumnMap;
    }

    public List<Map<String, Object>> getRelationExtraColumnList() {
        return relationExtraColumnList;
    }

    public void createRow(){
        this.relationExtraColumnList.add(new HashMap<>());
        this.currentIndex++;
    }
    public Map<String,Object> currentRow(){
        return this.relationExtraColumnList.get(this.currentIndex);
    }
}
