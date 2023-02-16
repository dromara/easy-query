package org.easy.query.core.abstraction;

import java.util.List;

/**
 * @FileName: EasyExecutor.java
 * @Description: 文件说明
 * @Date: 2023/2/16 22:20
 * @Created by xuejiaming
 */
public interface EasyExecutor {
    <TR> List<TR> execute(ExecutorContext executorContext,Class<TR> clazz,String sql,List<Object> parameters);
}
