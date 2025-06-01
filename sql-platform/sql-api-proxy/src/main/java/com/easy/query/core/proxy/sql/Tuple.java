package com.easy.query.core.proxy.sql;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.tuple.proxy.Tuple10Proxy;
import com.easy.query.core.proxy.core.tuple.proxy.Tuple1Proxy;
import com.easy.query.core.proxy.core.tuple.proxy.Tuple2Proxy;
import com.easy.query.core.proxy.core.tuple.proxy.Tuple3Proxy;
import com.easy.query.core.proxy.core.tuple.proxy.Tuple4Proxy;
import com.easy.query.core.proxy.core.tuple.proxy.Tuple5Proxy;
import com.easy.query.core.proxy.core.tuple.proxy.Tuple6Proxy;
import com.easy.query.core.proxy.core.tuple.proxy.Tuple7Proxy;
import com.easy.query.core.proxy.core.tuple.proxy.Tuple8Proxy;
import com.easy.query.core.proxy.core.tuple.proxy.Tuple9Proxy;

/**
 * create time 2024/1/18 19:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class Tuple {

    public <T1> Tuple1Proxy<T1> of(PropTypeColumn<T1> column1) {
        return new Tuple1Proxy<>(column1);
    }

    public <T1, T2> Tuple2Proxy<T1, T2> of(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2) {
        return new Tuple2Proxy<>(column1, column2);
    }

    public <T1, T2, T3> Tuple3Proxy<T1, T2, T3> of(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3) {
        return new Tuple3Proxy<>(column1, column2, column3);
    }

    public <T1, T2, T3, T4> Tuple4Proxy<T1, T2, T3, T4> of(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3, PropTypeColumn<T4> column4) {
        return new Tuple4Proxy<>(column1, column2, column3, column4);
    }

    public <T1, T2, T3, T4, T5> Tuple5Proxy<T1, T2, T3, T4, T5> of(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3, PropTypeColumn<T4> column4, PropTypeColumn<T5> column5) {
        return new Tuple5Proxy<>(column1, column2, column3, column4, column5);
    }

    public <T1, T2, T3, T4, T5, T6> Tuple6Proxy<T1, T2, T3, T4, T5, T6> of(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6) {
        return new Tuple6Proxy<>(column1, column2, column3, column4, column5,
                column6);
    }

    public <T1, T2, T3, T4, T5, T6, T7> Tuple7Proxy<T1, T2, T3, T4, T5, T6, T7> of(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
            PropTypeColumn<T7> column7) {
        return new Tuple7Proxy<>(column1, column2, column3, column4, column5,
                column6, column7);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8Proxy<T1, T2, T3, T4, T5, T6, T7, T8> of(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
            PropTypeColumn<T7> column7, PropTypeColumn<T8> column8) {
        return new Tuple8Proxy<>(column1, column2, column3, column4, column5,
                column6, column7,column8);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Tuple9Proxy<T1, T2, T3, T4, T5, T6, T7, T8, T9> of(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
            PropTypeColumn<T7> column7, PropTypeColumn<T8> column8, PropTypeColumn<T9> column9) {
        return new Tuple9Proxy<>(column1, column2, column3, column4, column5,
                column6, column7,column8,column9);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Tuple10Proxy<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> of(
            PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
            PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
            PropTypeColumn<T7> column7, PropTypeColumn<T8> column8, PropTypeColumn<T9> column9, PropTypeColumn<T10> column10) {
        return new Tuple10Proxy<>(column1, column2, column3, column4, column5,
                column6, column7,column8,column9,column10);

    }
}
