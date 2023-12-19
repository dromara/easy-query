package com.easy.query.core.proxy.core.draft;

import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.core.draft.proxy.Draft2Proxy;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/18 22:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class Draft2<T1, T2> extends AbstractDraft implements ProxyEntityAvailable<Draft2<T1, T2>, Draft2Proxy<T1, T2>> {
    private T1 value1;
    private T2 value2;

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

        @Override
    public void setValues(int index, Object value) {
        switch (index) {
            case 0:
                this.setValue1((T1) value);
                break;
            case 1:
                this.setValue2((T2) value);
                break;
        }
    }
//
//    @Override
//    public int capacity() {
//        return 2;
//    }
    @Override
    public Class<Draft2Proxy<T1, T2>> proxyTableClass() {
        return EasyObjectUtil.typeCastNullable(Draft2Proxy.class);
    }

    @Override
    public int capacity() {
        return 2;
    }
}
