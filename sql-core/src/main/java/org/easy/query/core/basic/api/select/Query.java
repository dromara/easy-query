package org.easy.query.core.basic.api.select;

import org.easy.query.core.query.SqlEntityQueryExpression;

/**
 * @FileName: Query.java
 * @Description: 文件说明
 * @Date: 2023/3/3 16:30
 * @Created by xuejiaming
 */
public interface Query<T> {
    /**
     * 当前查询对象的字节信息
     * @return 当前查询的对象字节
     */
    Class<T> queryClass();

    /**
     * 当前的查询表达式
     * @return
     */
    SqlEntityQueryExpression getSqlEntityExpression();
}
