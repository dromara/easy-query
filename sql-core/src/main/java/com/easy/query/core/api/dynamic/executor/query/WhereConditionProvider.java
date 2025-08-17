package com.easy.query.core.api.dynamic.executor.query;

import com.easy.query.core.annotation.EasyWhereCondition;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

/**
 * create time 2025/8/17 15:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface WhereConditionProvider {

    @NotNull
    EasyWhereCondition.Condition getQueryCondition(Object whereObject, Field field, Object val, EasyWhereCondition.Condition condition);
}
