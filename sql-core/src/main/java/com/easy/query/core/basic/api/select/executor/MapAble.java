package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.basic.api.select.QueryAvailable;

import java.util.List;
import java.util.Map;

/**
 * create time 2023/10/20 23:06
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MapAble<T> extends QueryAvailable<T> {

    /**
     * 将查询结果第一条映射到map中,map的key值忽略大小写,其中key为列名
     *
     * @return map结果
     */
    Map<String, Object> toMap();

    /**
     * 将查询结果映射到map中,map的key值忽略大小写,其中key为列名
     *
     * @return map集合结果
     */
    List<Map<String, Object>> toMaps();


}
