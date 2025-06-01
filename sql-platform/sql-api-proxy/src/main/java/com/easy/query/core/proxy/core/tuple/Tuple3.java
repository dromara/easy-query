package com.easy.query.core.proxy.core.tuple;

import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.core.draft.AbstractDraft;
import com.easy.query.core.proxy.core.tuple.proxy.Tuple3Proxy;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/18 22:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class Tuple3<T1, T2, T3> extends AbstractDraft implements ProxyEntityAvailable<Tuple3<T1, T2, T3>, Tuple3Proxy<T1, T2, T3>> {
    private T1 value1;
    private T2 value2;
    private T3 value3;

    public Tuple3() {}

    public Tuple3(T1 value1, T2 value2, T3 value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public T1 getValue1() {
        return value1;
    }

    public void setValue1(T1 value1) {
        this.value1 = value1;
    }

    public T2 getValue2() {
        return value2;
    }

    public void setValue2(T2 value2) {
        this.value2 = value2;
    }

    public T3 getValue3() {
        return value3;
    }

    public void setValue3(T3 value3) {
        this.value3 = value3;
    }

        @Override
    public void setValues(int index, Object value) {
        switch (index) {
            case 0:
                this.setValue1((T1) value);
                break;
            case 1:
                this.setValue2((T2) value);
                break;
            case 2:
                this.setValue3((T3) value);
                break;
        }
    }
//
//    @Override
//    public int capacity() {
//        return 3;
//    }

    @Override
    public int capacity() {
        return 3;
    }
}
