package org.easy.query.core.base;

/**
 * 非线程安全的lazy
 * @FileName: Lazy.java
 * @Description: 文件说明
 * @Date: 2023/2/11 14:27
 * @Created by xuejiaming
 */
public class Lazy<T> {
    private final Func<T> func;
    private T value;

    public Lazy(Func<T> func){

        this.func = func;
    }
    public T getValue(){
        if(value==null){
            value=func.invoke();
        }
        return value;
    }
}
