package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.basic.api.select.QueryAvailable;

import java.util.List;

/**
 * create time 2023/10/20 23:06
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ListResultAble<T> extends QueryAvailable<T> {


    /**
     * 返回所有的查询结果集
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param resultClass 映射对象
     * @param <TR>        映射对象类型
     * @return 获取查询结果集
     */
    <TR> List<TR> toList(Class<TR> resultClass);
}
