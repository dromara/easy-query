package com.easy.query.core.expression.executor.parser.descriptor;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.EasyClassUtil;

import java.util.Collections;
import java.util.List;

/**
 * create time 2023/5/18 17:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableEntityParseDescriptor extends TableParseDescriptor{
    default List<Object> getEntitiesNotNull(TableAvailable table){
        List<Object> entities = getEntitiesOrNull(table);
        if(entities==null){
            throw new EasyQueryInvalidOperationException("cant found entities from table entity parse descriptor:"+ EasyClassUtil.getSimpleName(table.getEntityClass()));
        }
        return entities;
    }
    List<Object> getEntitiesOrNull(TableAvailable table);
}
