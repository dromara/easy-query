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

/**
 * create time 2024/1/18 19:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class Draft {

    public <T1> Draft1Proxy<T1> of(PropTypeColumn<T1> column1) {
        Draft1Proxy<T1> draft1Proxy = new Draft1Proxy<>();
        draft1Proxy.fetch(0,column1,draft1Proxy.value1());
        return draft1Proxy;
    }

    public <T1, T2> Draft2Proxy<T1, T2> of(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2) {
        Draft2Proxy<T1, T2> draft2Proxy = new Draft2Proxy<>();
        draft2Proxy.fetch(0,column1,draft2Proxy.value1());
        draft2Proxy.fetch(1,column2,draft2Proxy.value2());
        return draft2Proxy;
    }

    public <T1, T2, T3> Draft3Proxy<T1, T2, T3> of(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3) {
        Draft3Proxy<T1, T2, T3> draft3Proxy = new Draft3Proxy<>();
        draft3Proxy.fetch(0,column1,draft3Proxy.value1());
        draft3Proxy.fetch(1,column2,draft3Proxy.value2());
        draft3Proxy.fetch(2,column3,draft3Proxy.value3());
        return draft3Proxy;
    }

    public <T1, T2, T3, T4> Draft4Proxy<T1, T2, T3, T4> of(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3, PropTypeColumn<T4> column4) {
        Draft4Proxy<T1, T2, T3, T4> draft4Proxy = new Draft4Proxy<>();
        draft4Proxy.fetch(0,column1,draft4Proxy.value1());
        draft4Proxy.fetch(1,column2,draft4Proxy.value2());
        draft4Proxy.fetch(2,column3,draft4Proxy.value3());
        draft4Proxy.fetch(3,column4,draft4Proxy.value4());
        return draft4Proxy;
    }

    public <T1, T2, T3, T4, T5> Draft5Proxy<T1, T2, T3, T4, T5> of(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3, PropTypeColumn<T4> column4, PropTypeColumn<T5> column5) {
        Draft5Proxy<T1, T2, T3, T4, T5> draft5Proxy = new Draft5Proxy<>();
        draft5Proxy.fetch(0,column1,draft5Proxy.value1());
        draft5Proxy.fetch(1,column2,draft5Proxy.value2());
        draft5Proxy.fetch(2,column3,draft5Proxy.value3());
        draft5Proxy.fetch(3,column4,draft5Proxy.value4());
        draft5Proxy.fetch(4,column5,draft5Proxy.value5());
        return draft5Proxy;
    }

    public <T1, T2, T3, T4, T5, T6> Draft6Proxy<T1, T2, T3, T4, T5, T6> of(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6) {
        Draft6Proxy<T1, T2, T3, T4, T5, T6> draft6Proxy = new Draft6Proxy<>();
        draft6Proxy.fetch(0,column1,draft6Proxy.value1());
        draft6Proxy.fetch(1,column2,draft6Proxy.value2());
        draft6Proxy.fetch(2,column3,draft6Proxy.value3());
        draft6Proxy.fetch(3,column4,draft6Proxy.value4());
        draft6Proxy.fetch(4,column5,draft6Proxy.value5());
        draft6Proxy.fetch(5,column6,draft6Proxy.value6());
        return draft6Proxy;
    }

    public <T1, T2, T3, T4, T5, T6, T7> Draft7Proxy<T1, T2, T3, T4, T5, T6, T7> of(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
            PropTypeColumn<T7> column7) {
        Draft7Proxy<T1, T2, T3, T4, T5, T6, T7> draft7Proxy = new Draft7Proxy<>();
        draft7Proxy.fetch(0,column1,draft7Proxy.value1());
        draft7Proxy.fetch(1,column2,draft7Proxy.value2());
        draft7Proxy.fetch(2,column3,draft7Proxy.value3());
        draft7Proxy.fetch(3,column4,draft7Proxy.value4());
        draft7Proxy.fetch(4,column5,draft7Proxy.value5());
        draft7Proxy.fetch(5,column6,draft7Proxy.value6());
        draft7Proxy.fetch(6,column7,draft7Proxy.value7());
        return draft7Proxy;
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> Draft8Proxy<T1, T2, T3, T4, T5, T6, T7, T8> of(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
            PropTypeColumn<T7> column7, PropTypeColumn<T8> column8) {
        Draft8Proxy<T1, T2, T3, T4, T5, T6, T7, T8> draft8Proxy = new Draft8Proxy<>();
        draft8Proxy.fetch(0,column1,draft8Proxy.value1());
        draft8Proxy.fetch(1,column2,draft8Proxy.value2());
        draft8Proxy.fetch(2,column3,draft8Proxy.value3());
        draft8Proxy.fetch(3,column4,draft8Proxy.value4());
        draft8Proxy.fetch(4,column5,draft8Proxy.value5());
        draft8Proxy.fetch(5,column6,draft8Proxy.value6());
        draft8Proxy.fetch(6,column7,draft8Proxy.value7());
        draft8Proxy.fetch(7,column8,draft8Proxy.value8());
        return draft8Proxy;
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Draft9Proxy<T1, T2, T3, T4, T5, T6, T7, T8, T9> of(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
            PropTypeColumn<T7> column7, PropTypeColumn<T8> column8, PropTypeColumn<T9> column9) {
        Draft9Proxy<T1, T2, T3, T4, T5, T6, T7, T8, T9> draft9Proxy = new Draft9Proxy<>();
        draft9Proxy.fetch(0,column1,draft9Proxy.value1());
        draft9Proxy.fetch(1,column2,draft9Proxy.value2());
        draft9Proxy.fetch(2,column3,draft9Proxy.value3());
        draft9Proxy.fetch(3,column4,draft9Proxy.value4());
        draft9Proxy.fetch(4,column5,draft9Proxy.value5());
        draft9Proxy.fetch(5,column6,draft9Proxy.value6());
        draft9Proxy.fetch(6,column7,draft9Proxy.value7());
        draft9Proxy.fetch(7,column8,draft9Proxy.value8());
        draft9Proxy.fetch(8,column9,draft9Proxy.value9());
        return draft9Proxy;
    }
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Draft10Proxy<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> of(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
            PropTypeColumn<T7> column7, PropTypeColumn<T8> column8, PropTypeColumn<T9> column9, PropTypeColumn<T10> column10) {
        Draft10Proxy<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> draft10Proxy = new Draft10Proxy<>();

        draft10Proxy.fetch(0,column1,draft10Proxy.value1());
        draft10Proxy.fetch(1,column2,draft10Proxy.value2());
        draft10Proxy.fetch(2,column3,draft10Proxy.value3());
        draft10Proxy.fetch(3,column4,draft10Proxy.value4());
        draft10Proxy.fetch(4,column5,draft10Proxy.value5());
        draft10Proxy.fetch(5,column6,draft10Proxy.value6());
        draft10Proxy.fetch(6,column7,draft10Proxy.value7());
        draft10Proxy.fetch(7,column8,draft10Proxy.value8());
        draft10Proxy.fetch(8,column9,draft10Proxy.value9());
        draft10Proxy.fetch(9,column10,draft10Proxy.value10());
        return draft10Proxy;
    }
}
