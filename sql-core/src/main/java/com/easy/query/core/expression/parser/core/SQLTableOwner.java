package com.easy.query.core.expression.parser.core;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2023/7/2 21:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLTableOwner {
    TableAvailable getTable();
   default @Nullable EntityMetadata getEntityMetadata(){
       if(getTable()==null){
           return null;
       }
       return getTable().getEntityMetadata();
   }
}
