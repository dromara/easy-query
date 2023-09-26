package com.easy.query.core.expression.segment.scec.expression;

import java.util.function.Consumer;

/**
 * create time 2023/9/26 18:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnMarkQuestion {
    private final ColumnParamExpression columnParamExpression;
    private final Consumer<ColumnParamExpression> addParamsConsume;

    public ColumnMarkQuestion(ColumnParamExpression columnParamExpression,Consumer<ColumnParamExpression> addParamsConsume){
        this.columnParamExpression = columnParamExpression;

        this.addParamsConsume = addParamsConsume;
    }
    @Override
    public String toString() {
        addParamsConsume.accept(columnParamExpression);
        return "?";
    }
}
