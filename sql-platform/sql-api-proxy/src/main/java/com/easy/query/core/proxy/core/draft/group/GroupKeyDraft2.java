package com.easy.query.core.proxy.core.draft.group;

/**
 * create time 2023/12/23 13:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupKeyDraft2<TKey1,TKey2> extends AbstractGroupKey {
    private TKey1 key1;
    private TKey2 key2;

    public TKey1 getKey1() {
        return key1;
    }

    public void setKey1(TKey1 key1) {
        this.key1 = key1;
    }

    public TKey2 getKey2() {
        return key2;
    }

    public void setKey2(TKey2 key2) {
        this.key2 = key2;
    }

    @Override
    public int capacity() {
        return 2;
    }

    @Override
    public void setValues(int index, Object value) {
        switch (index) {
            case 0:
                this.setKey1((TKey1) value);
                break;
            case 1:
                this.setKey2((TKey2) value);
                break;
        }
    }
}
