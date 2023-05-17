package com.easy.query.core.basic.api.update;

import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.SQLExecuteExpectRows;
import com.easy.query.core.basic.api.internal.TableReNameable;

/**
 * @FileName: Update.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:04
 * @author xuejiaming
 */
public interface Updatable<T,TChain> extends SQLExecuteExpectRows, Interceptable<TChain>, LogicDeletable<TChain>, TableReNameable<TChain> {

}
