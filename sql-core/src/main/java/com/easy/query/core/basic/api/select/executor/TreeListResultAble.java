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
     * 返回所有的查询结果集
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @return 获取查询结果集
     */
    List<T> toTreeList();
}
