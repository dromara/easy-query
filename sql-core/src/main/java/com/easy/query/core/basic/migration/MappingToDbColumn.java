package com.easy.query.core.basic.migration;

/**
 * create time 2023/11/30 09:42
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MappingToDbColumn {
     String toDbColumn(String dbTypeFull,Boolean unsigned, Boolean nullable, Object defaultValue);
}
