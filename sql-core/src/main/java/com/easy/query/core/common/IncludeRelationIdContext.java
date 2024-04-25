package com.easy.query.core.common;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2024/4/25 08:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class IncludeRelationIdContext implements IncludeRelationIdAvailable{
     private final List<Object> relationIds;
     public IncludeRelationIdContext(){
         relationIds=new ArrayList<>();
     }

    public List<Object> getRelationIds() {
        return relationIds;
    }
}
