package com.easy.query.search.executor;

import com.easy.query.search.op.Op;
import com.easy.query.core.annotation.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 条件参数
 *
 * @author bkbits
 */
class EasySearchParam {
    private final EasySearchParamName paramName; //参数名
    private final Op op; //运算符实例
    private final List<EasySearchValue> params = new ArrayList<>(); //参数值

    EasySearchParam(
            @NotNull EasySearchParamName paramName,
            @NotNull Op op
    ) {
        this.paramName = paramName;
        this.op = op;
    }

    public @NotNull EasySearchParamName getParamName() {
        return paramName;
    }

    public @NotNull Op getOp() {
        return op;
    }

    public @NotNull EasySearchParam sort() {
        params.sort(Comparator.comparing(EasySearchValue::getIndex));
        return this;
    }

    public @NotNull List<EasySearchValue> getParams() {
        return params;
    }

    public @NotNull EasySearchParam addParam(Object value) {
        return addParam(value, 0);
    }

    public @NotNull EasySearchParam addParam(Object value, int index) {
        params.add(new EasySearchValue(index, value));
        return this;
    }
}
