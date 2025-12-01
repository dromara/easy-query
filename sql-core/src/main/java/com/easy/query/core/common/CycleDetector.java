package com.easy.query.core.common;

import java.util.HashMap;
import java.util.Map;

/**
 * create time 2024/2/26 21:43
 * 循环检测器
 *
 * @author xuejiaming
 */
public class CycleDetector {
    private Map<RelationPath, RelationPath> relationPathMap = new HashMap<>();
    public CycleDetector(){

    }

    public boolean relationPathRepeat(RelationPath relationPath) {
        RelationPath oldRelationPath = relationPathMap.get(relationPath);
        if(oldRelationPath ==null){
            relationPathMap.put(relationPath, relationPath);
            return false;
        }
        if(oldRelationPath.getDeep()< relationPath.getDeep()){
            return true;
        }
        oldRelationPath.setDeep(relationPath.getDeep());
        return false;
    }
}
