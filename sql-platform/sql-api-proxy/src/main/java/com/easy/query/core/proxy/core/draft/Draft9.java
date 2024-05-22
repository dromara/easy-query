package com.easy.query.core.proxy.core.draft;

import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.core.draft.proxy.Draft9Proxy;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/19 09:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class Draft9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends AbstractDraft implements ProxyEntityAvailable<Draft9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, Draft9Proxy<T1, T2, T3, T4, T5, T6, T7, T8, T9>> {
    private T1 value1;
    private T2 value2;
    private T3 value3;
    private T4 value4;
    private T5 value5;
    private T6 value6;
    private T7 value7;
    private T8 value8;
    private T9 value9;

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
    public T7 getValue7() {
        return value7;
    }

    public void setValue7(T7 value7) {
        this.value7 = value7;
    }
    public T8 getValue8() {
        return value8;
    }

    public void setValue8(T8 value8) {
        this.value8 = value8;
    }

    public T9 getValue9() {
        return value9;
    }

    public void setValue9(T9 value9) {
        this.value9 = value9;
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
            case 6:
                this.setValue7((T7) value);
                break;
            case 7:
                this.setValue8((T8) value);
                break;
            case 8:
                this.setValue9((T9) value);
                break;
        }
    }

    @Override
    public int capacity() {
        return 9;
    }
}