package com.easy.query.core.proxy.sql.draft;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.draft.proxy.Draft1Proxy;
import com.easy.query.core.proxy.sql.Select;

/**
 * create time 2025/5/19 14:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class Draft1Builder<T1> {
    private final PropTypeColumn<T1> column1;

    public Draft1Builder(PropTypeColumn<T1> column1) {
        this.column1 = column1;
    }

    public <T2> Draft2Builder<T1, T2> value2(PropTypeColumn<T2> column2) {
        return new Draft2Builder<>(column1, column2);
    }

    public Draft1Proxy<T1> build() {
        return Select.DRAFT.of(column1);
    }
}
