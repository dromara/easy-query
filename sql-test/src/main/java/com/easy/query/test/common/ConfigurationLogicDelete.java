package com.easy.query.test.common;

import com.easy.query.core.basic.extension.logicdel.LogicDeleteBuilder;
import com.easy.query.core.basic.extension.logicdel.abstraction.AbstractConfigurationLogicDeleteStrategy;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.test.dto.MyConfigLogicDelete;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2026/1/30 20:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class ConfigurationLogicDelete extends AbstractConfigurationLogicDeleteStrategy {
    @Override
    protected SQLActionExpression1<WherePredicate<Object>> getPredicateFilterExpression() {
        return o -> o.eq("deleted", false);
    }

    @Override
    protected SQLActionExpression1<ColumnSetter<Object>> getDeletedSQLExpression() {
        return o -> o.set("deleted", true);
    }

    @Override
    public String getStrategy() {
        return ConfigurationLogicDelete.class.getName();
    }

    @Override
    public boolean apply(@NotNull Class<?> entityClass) {
        return MyConfigLogicDelete.class.equals(entityClass);
    }
}
