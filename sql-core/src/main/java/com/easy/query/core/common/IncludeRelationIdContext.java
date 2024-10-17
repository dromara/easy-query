package com.easy.query.core.common;

import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * create time 2024/4/25 08:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class IncludeRelationIdContext implements IncludeRelationIdAvailable{
     private final List<List<Object>> relationIds;
     public IncludeRelationIdContext(){
         relationIds=new ArrayList<>();
     }

    public List<List<Object>> getRelationIds() {
        return relationIds;
    }
}
