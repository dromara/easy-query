package com.easy.query.core.basic.api.internal;

import java.util.function.Function;

/**
 * create time 2024/2/26 20:31
 * 仅生效对一的导航属性对多的导航属性请自行设置
 *
 * @author xuejiaming
 */
public interface RelationLogicDeletable<TChain> {

    /**
     * 前一个表是否使用逻辑删除
     * @param tableLogicDel 返回结果 true表示使用逻辑删除,返回结果 false表示不使用逻辑删除,返回null表示不设置
     * @param tableLogicDel 第一个参数表示关系对象类型第二个表示是否使用逻辑删除 true表示使用逻辑删除,返回结果 false表示不使用逻辑删除
     * @return
     */
    TChain relationLogicDelete(Function<Class<?>,Boolean> tableLogicDel);
}
