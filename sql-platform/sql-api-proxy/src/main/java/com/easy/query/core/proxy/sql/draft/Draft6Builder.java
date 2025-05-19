package com.easy.query.core.proxy.sql.draft;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.draft.proxy.Draft6Proxy;
import com.easy.query.core.proxy.sql.Select;

/**
 * create time 2025/5/19 15:09
 * 文件说明
 *
 * @author xuejiaming
 */

public class Draft6Builder<T1, T2, T3, T4, T5, T6> {
    private final PropTypeColumn<T1> column1;
    private final PropTypeColumn<T2> column2;
    private final PropTypeColumn<T3> column3;
    private final PropTypeColumn<T4> column4;
    private final PropTypeColumn<T5> column5;
    private final PropTypeColumn<T6> column6;

    public Draft6Builder(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2,
                         PropTypeColumn<T3> column3, PropTypeColumn<T4> column4,
                         PropTypeColumn<T5> column5, PropTypeColumn<T6> column6) {
        this.column1 = column1;
        this.column2 = column2;
        this.column3 = column3;
        this.column4 = column4;
        this.column5 = column5;
        this.column6 = column6;
    }

    public <T7> Draft7Builder<T1, T2, T3, T4, T5, T6, T7> value7(PropTypeColumn<T7> column7) {
        return new Draft7Builder<>(column1, column2, column3, column4, column5, column6, column7);
    }

    public Draft6Proxy<T1, T2, T3, T4, T5, T6> build() {
        return Select.DRAFT.of(column1, column2, column3, column4, column5, column6);
    }
}