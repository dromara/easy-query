package com.easy.query.core.basic.api.cte;

/**
 * create time 2025/2/7 10:40
 * with cte as 生成的临时表的默认命名提供者
 *
 * @author xuejiaming
 */
public interface CteTableNamedProvider {
    /**
     * 返回默认的cte表名称
     * @param clazz 表达式返回的类型
     * @return cte表名称
     */
    String getDefaultCteTableName(Class<?> clazz);
}
