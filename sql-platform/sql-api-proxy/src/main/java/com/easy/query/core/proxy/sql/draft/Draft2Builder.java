package com.easy.query.core.proxy.sql.draft;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.draft.proxy.Draft1Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft2Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft3Proxy;
import com.easy.query.core.proxy.sql.Select;

/**
 * create time 2025/5/19 14:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class Draft2Builder<T1, T2> {
    private final PropTypeColumn<T1> column1;
    private final PropTypeColumn<T2> column2;

    public Draft2Builder(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2) {
        this.column1 = column1;
        this.column2 = column2;
    }

    public <T3> Draft3Builder<T1, T2, T3> value3(PropTypeColumn<T3> column3) {
        return new Draft3Builder<>(column1, column2, column3);
    }
    public Draft2Proxy<T1, T2> build() {
        return Select.DRAFT.of(column1, column2);
    }
}
