package com.easy.query.core.proxy.core.draft;

import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.core.draft.proxy.Draft1Proxy;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/18 22:09
 * 草稿用于返回草稿结果
 *
 * @author xuejiaming
 */
public class Draft1<T1> extends AbstractDraft implements ProxyEntityAvailable<Draft1<T1> , Draft1Proxy<T1>> {
//    @Override
//    public int capacity() {
//        return 1;
//    }


    private T1 value1;

    public Draft1(T1 value1){
        this.value1 = value1;
    }
    public Draft1(){
    }

    public T1 getValue1() {
        return value1;
    }

    public void setValue1(T1 value1) {
        this.value1 = value1;
    }

    @Override
    public void setValues(int index, Object value) {
        switch (index) {
            case 0:
                this.setValue1((T1) value);
                break;
        }
    }

    @Override
    public int capacity() {
        return 1;
    }
}
