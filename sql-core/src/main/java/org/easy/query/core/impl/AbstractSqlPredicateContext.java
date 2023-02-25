package org.easy.query.core.impl;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.query.builder.SqlTableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: AbstractSqlContext.java
 * @Description: 文件说明
 * @Date: 2023/2/23 21:57
 * @Created by xuejiaming
 */
public abstract class AbstractSqlPredicateContext extends AbstractSqlContext implements SqlPredicateContext{

    public AbstractSqlPredicateContext(EasyQueryRuntimeContext runtimeContext){
        super(runtimeContext);
    }
}
