package com.easy.query.api.proxy.extension.window;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.PropTypeColumn;

/**
 * create time 2025/10/11 13:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class NextSelector<TProperty, TChain> {

    private final SQLFuncExpression1<Offset<TProperty>, TChain> createNextColumn;

    public NextSelector(SQLFuncExpression1<Offset<TProperty>, TChain> createNextColumn) {
        this.createNextColumn = createNextColumn;
    }

    public TChain next(int offset) {
        checkOffset(offset);
        Offset<TProperty> of = Offset.of(offset);
        return createNextColumn.apply(of);
    }

    public TChain next(int offset, PropTypeColumn<TProperty> defaultColumn) {
        checkOffset(offset);
        Offset<TProperty> of = Offset.of(offset, defaultColumn);
        return createNextColumn.apply(of);
    }

    public TChain next(int offset, TProperty defaultValue) {
        checkOffset(offset);
        Offset<TProperty> of = Offset.of(offset, defaultValue);
        return createNextColumn.apply(of);
    }

    public TChain prev(int offset) {
        checkOffset(offset);
        Offset<TProperty> of = Offset.of(offset * -1);
        return createNextColumn.apply(of);
    }

    public TChain prev(int offset, PropTypeColumn<TProperty> defaultColumn) {
        checkOffset(offset);
        Offset<TProperty> of = Offset.of(offset * -1, defaultColumn);
        return createNextColumn.apply(of);
    }

    public TChain prev(int offset, TProperty defaultValue) {
        checkOffset(offset);
        Offset<TProperty> of = Offset.of(offset * -1, defaultValue);
        return createNextColumn.apply(of);
    }

    private void checkOffset(int offset) {
        if (offset < 0) {
            throw new EasyQueryInvalidOperationException("offset must be greater than or equal to 0 (offset >= 0)");
        }
    }


    public TChain firstValue() {
        return createNextColumn.apply(new FirstValueOffset<>());
    }

    public TChain lastValue() {
        return createNextColumn.apply(new LastValueOffset<>());
    }

    public TChain nthValue(int offset) {
        checkOffset(offset);
        return createNextColumn.apply(new NthValueOffset<>(offset));
    }
}
