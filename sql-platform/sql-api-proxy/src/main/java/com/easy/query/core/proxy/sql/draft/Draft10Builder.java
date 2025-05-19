package com.easy.query.core.proxy.sql.draft;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.draft.proxy.Draft10Proxy;
import com.easy.query.core.proxy.sql.Select;

/**
 * create time 2025/5/19 15:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class Draft10Builder<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {
    private final PropTypeColumn<T1> column1;
    private final PropTypeColumn<T2> column2;
    private final PropTypeColumn<T3> column3;
    private final PropTypeColumn<T4> column4;
    private final PropTypeColumn<T5> column5;
    private final PropTypeColumn<T6> column6;
    private final PropTypeColumn<T7> column7;
    private final PropTypeColumn<T8> column8;
    private final PropTypeColumn<T9> column9;
    private final PropTypeColumn<T10> column10;

    public Draft10Builder(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2,
                          PropTypeColumn<T3> column3, PropTypeColumn<T4> column4,
                          PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
                          PropTypeColumn<T7> column7, PropTypeColumn<T8> column8,
                          PropTypeColumn<T9> column9, PropTypeColumn<T10> column10) {
        this.column1 = column1;
        this.column2 = column2;
        this.column3 = column3;
        this.column4 = column4;
        this.column5 = column5;
        this.column6 = column6;
        this.column7 = column7;
        this.column8 = column8;
        this.column9 = column9;
        this.column10 = column10;
    }

    public Draft10Proxy<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> build() {
        return Select.DRAFT.of(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10);
    }
}