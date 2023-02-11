package org.jdqc.core.base;

import java.util.function.Function;

/**
 * @FileName: Lazy.java
 * @Description: 文件说明
 * @Date: 2023/2/11 14:27
 * @Created by xuejiaming
 */
public class Lazy<T> {
    private final Func<T> func;

    public Lazy(Func<T> func){

        this.func = func;
    }
}
