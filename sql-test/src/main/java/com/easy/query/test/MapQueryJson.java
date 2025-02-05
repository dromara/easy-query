package com.easy.query.test;

import lombok.Data;

import java.util.List;

/**
 * create time 2025/2/5 12:37
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class MapQueryJson {
    private String tableName;
    private int tableIndex;

    public List<InternalOn> onFilters;
    public List<InternalWhere> whereFilters;

    @Data
    public static class InternalOn{
        /**
         * 比较的表索引
         */
        private Integer tableIndex;
        /**
         * 当前列
         */
        private String column;
        /**
         * 比较列或者条件
         */
        private Object compare;
    }
    @Data
    public static class InternalWhere{

        /**
         * 当前列
         */
        private String column;
        /**
         * 比较列或者条件
         */
        private Object compare;
    }
}
