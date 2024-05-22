package com.easy.query.core.proxy.core.draft;

import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.core.draft.proxy.Draft4Proxy;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/18 22:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class Draft4<T1, T2, T3, T4> extends AbstractDraft implements ProxyEntityAvailable<Draft4<T1, T2, T3, T4>, Draft4Proxy<T1, T2, T3, T4>> {
    private T1 value1;
    private T2 value2;
    private T3 value3;
    private T4 value4;

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
        }
    }

    @Override
    public int capacity() {
        return 4;
    }
}
