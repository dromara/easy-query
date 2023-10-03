package com.easy.query.api4j.insert.map;

import com.easy.query.core.basic.api.insert.map.MapClientInsertable;

import java.util.Map;

/**
 * create time 2023/10/3 09:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyMapInsertable extends AbstractMapInsertable {
    public EasyMapInsertable(MapClientInsertable<Map<String, Object>> mapClientInsertable) {
        super(mapClientInsertable);
    }
}
