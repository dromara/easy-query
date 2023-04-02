package com.easy.query.core.basic.api.update;

import com.easy.query.core.basic.api.insert.Insertable;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.SqlExecuteExpectRows;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.Queryable;

import java.util.function.Function;

/**
 * @FileName: Update.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:04
 * @author xuejiaming
 */
public interface Updatable<T,TChain> extends SqlExecuteExpectRows, Interceptable<TChain>, LogicDeletable<TChain>, TableReNameable<TChain> {

}
