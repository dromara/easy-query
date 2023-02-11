package org.jdqc.core.annotation;

/**
 * @FileName: PrimaryKey.java
 * @Description: 文件说明
 * @Date: 2023/2/11 11:16
 * @Created by xuejiaming
 */
public @interface PrimaryKey {
    /**
     * 是否是自增键
     */
    boolean increment() default false;
}
