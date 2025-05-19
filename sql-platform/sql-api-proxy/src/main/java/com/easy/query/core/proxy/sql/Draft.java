package com.easy.query.core.proxy.sql;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.draft.proxy.Draft10Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft1Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft2Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft3Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft4Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft5Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft6Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft7Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft8Proxy;
import com.easy.query.core.proxy.core.draft.proxy.Draft9Proxy;
import com.easy.query.core.proxy.sql.draft.Draft1Builder;

/**
 * create time 2024/1/18 19:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class Draft {

    public <T1> Draft1Builder<T1> value1(PropTypeColumn<T1> column1) {
        return new Draft1Builder<>(column1);
    }


    public <T1> Draft1Proxy<T1> of(PropTypeColumn<T1> column1) {
        return new Draft1Proxy<>(column1);
    }

    public <T1, T2> Draft2Proxy<T1, T2> of(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2) {
        return new Draft2Proxy<>(column1, column2);
    }

    public <T1, T2, T3> Draft3Proxy<T1, T2, T3> of(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3) {
        return new Draft3Proxy<>(column1, column2, column3);
    }

    public <T1, T2, T3, T4> Draft4Proxy<T1, T2, T3, T4> of(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3, PropTypeColumn<T4> column4) {
        return new Draft4Proxy<>(column1, column2, column3, column4);
    }

    public <T1, T2, T3, T4, T5> Draft5Proxy<T1, T2, T3, T4, T5> of(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3, PropTypeColumn<T4> column4, PropTypeColumn<T5> column5) {
        return new Draft5Proxy<>(column1, column2, column3, column4, column5);
    }

    public <T1, T2, T3, T4, T5, T6> Draft6Proxy<T1, T2, T3, T4, T5, T6> of(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6) {
        return new Draft6Proxy<>(column1, column2, column3, column4, column5,
                column6);
    }

    public <T1, T2, T3, T4, T5, T6, T7> Draft7Proxy<T1, T2, T3, T4, T5, T6, T7> of(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
            PropTypeColumn<T7> column7) {
        return new Draft7Proxy<>(column1, column2, column3, column4, column5,
                column6, column7);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> Draft8Proxy<T1, T2, T3, T4, T5, T6, T7, T8> of(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
            PropTypeColumn<T7> column7, PropTypeColumn<T8> column8) {
        return new Draft8Proxy<>(column1, column2, column3, column4, column5,
                column6, column7,column8);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Draft9Proxy<T1, T2, T3, T4, T5, T6, T7, T8, T9> of(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
            PropTypeColumn<T7> column7, PropTypeColumn<T8> column8, PropTypeColumn<T9> column9) {
        return new Draft9Proxy<>(column1, column2, column3, column4, column5,
                column6, column7,column8,column9);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Draft10Proxy<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> of(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
            PropTypeColumn<T7> column7, PropTypeColumn<T8> column8, PropTypeColumn<T9> column9, PropTypeColumn<T10> column10) {
        return new Draft10Proxy<>(column1, column2, column3, column4, column5,
                column6, column7,column8,column9,column10);

    }
}
