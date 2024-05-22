package com.easy.query.core.proxy.core.draft;

import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.core.draft.proxy.Draft6Proxy;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/19 09:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class Draft6<T1, T2, T3, T4, T5, T6> extends AbstractDraft implements ProxyEntityAvailable<Draft6<T1, T2, T3, T4, T5, T6>, Draft6Proxy<T1, T2, T3, T4, T5, T6>> {
    private T1 value1;
    private T2 value2;
    private T3 value3;
    private T4 value4;
    private T5 value5;
    private T6 value6;


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

    public T4 getValue4() {
        return value4;
    }

    public void setValue4(T4 value4) {
        this.value4 = value4;
    }
    public T5 getValue5() {
        return value5;
    }

    public void setValue5(T5 value5) {
        this.value5 = value5;
    }
    public T6 getValue6() {
        return value6;
    }

    public void setValue6(T6 value6) {
        this.value6 = value6;
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
            case 3:
                this.setValue4((T4) value);
                break;
            case 4:
                this.setValue5((T5) value);
                break;
            case 5:
                this.setValue6((T6) value);
                break;
        }
    }

    @Override
    public int capacity() {
        return 6;
    }
}