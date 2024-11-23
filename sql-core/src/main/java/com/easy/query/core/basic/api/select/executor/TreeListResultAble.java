package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.basic.api.select.QueryAvailable;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.List;

/**
 * create time 2023/10/20 23:06
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TreeListResultAble<T> extends QueryAvailable<T> {


    /**
     *
     * 返回所有的查询结果集
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * 不支持 asTreeCTECustom 的树查询 因为不确定是否包含自身对象
     *
     * @param ignore 如果不存在children为自身的是否报错
     * @return 获取查询结果集
     */
    List<T> toTreeList(boolean ignore);

   default List<T> toTreeList(){
       return toTreeList(false);
   }
}
