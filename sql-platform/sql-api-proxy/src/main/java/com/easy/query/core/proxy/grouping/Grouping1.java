package com.easy.query.core.proxy.grouping;


import com.easy.query.core.proxy.core.draft.AbstractDraft;

/**
 * create time 2023/12/28 14:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class Grouping1<TKey1> extends AbstractDraft {
    private TKey1 key1;

    public TKey1 getKey1() {
        return key1;
    }

    public void setKey1(TKey1 key1) {
        this.key1 = key1;
    }

    @Override
    public int capacity() {
        return 1;
    }

    @Override
    public void setValues(int index, Object value) {
        switch (index) {
            case 0:
                this.setKey1((TKey1) value);
                break;
        }
    }

}