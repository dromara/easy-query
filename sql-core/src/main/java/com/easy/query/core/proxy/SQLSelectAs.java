package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.AsSelector;

/**
 * create time 2023/12/1 22:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelectAs extends SQLSelect,SQLGroupSelect {
    default void accept(AsSelector f){
        f.column(this.getTable(),this.value());
    }
}
