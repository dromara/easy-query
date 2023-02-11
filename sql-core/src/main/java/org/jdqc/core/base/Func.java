package org.jdqc.core.base;

/**
 * @FileName: Func.java
 * @Description: 文件说明
 * @Date: 2023/2/11 14:29
 * @Created by xuejiaming
 */
@FunctionalInterface
public interface Func<TR> {
    TR invoke();
}
