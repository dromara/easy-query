package com.easy.query.core.api.dynamic.executor.query;

import com.easy.query.core.annotation.EasyWhereCondition;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.enums.DefaultConditionEnum;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * create time 2025/8/17 15:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultWhereConditionProvider implements WhereConditionProvider {
    private final EasyQueryOption easyQueryOption;

    public DefaultWhereConditionProvider(QueryConfiguration queryConfiguration) {
        this.easyQueryOption = queryConfiguration.getEasyQueryOption();
    }

    @NotNull
    @Override
    public EasyWhereCondition.Condition getQueryCondition(Object whereObject, Field field, Object val, EasyWhereCondition.Condition condition) {
        if (condition == EasyWhereCondition.Condition.DEFAULT) {
            if (Objects.equals(String.class, field.getType())) {
                if (easyQueryOption.getDefaultCondition() == DefaultConditionEnum.CONTAINS) {
                    return EasyWhereCondition.Condition.CONTAINS;
                }
                return EasyWhereCondition.Condition.LIKE;
            }
            return EasyWhereCondition.Condition.EQUAL;
        }
        return condition;
    }
}
