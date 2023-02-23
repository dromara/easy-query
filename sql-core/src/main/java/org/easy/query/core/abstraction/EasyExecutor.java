package org.easy.query.core.abstraction;

import java.util.List;

/**
 * @FileName: EasyExecutor.java
 * @Description: 文件说明
 * @Date: 2023/2/16 22:20
 * @Created by xuejiaming
 */
public interface EasyExecutor {
    <T> long insert(ExecutorContext executorContext, Class<T> clazz, String sql, List<T> entities,List<String> properties);
    <TR> List<TR> query(ExecutorContext executorContext, Class<TR> clazz, String sql, List<Object> parameters);
}
