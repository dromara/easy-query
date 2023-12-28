package com.easy.query.core.proxy.grouping;

import com.easy.query.core.proxy.core.draft.AbstractDraft;

/**
 * create time 2023/12/28 21:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class Grouping7<TKey1, TKey2, TKey3, TKey4, TKey5, TKey6, TKey7> extends AbstractDraft {
    private TKey1 key1;
    private TKey2 key2;
    private TKey3 key3;
    private TKey4 key4;
    private TKey5 key5;
    private TKey6 key6;
    private TKey7 key7;

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

    public TKey3 getKey3() {
        return key3;
    }

    public void setKey3(TKey3 key3) {
        this.key3 = key3;
    }

    public TKey4 getKey4() {
        return key4;
    }

    public void setKey4(TKey4 key4) {
        this.key4 = key4;
    }

    public TKey5 getKey5() {
        return key5;
    }

    public void setKey5(TKey5 key5) {
        this.key5 = key5;
    }

    public TKey6 getKey6() {
        return key6;
    }

    public void setKey6(TKey6 key6) {
        this.key6 = key6;
    }

    public TKey7 getKey7() {
        return key7;
    }

    public void setKey7(TKey7 key7) {
        this.key7 = key7;
    }

    @Override
    public int capacity() {
        return 7;
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
            case 2:
                this.setKey3((TKey3) value);
                break;
            case 3:
                this.setKey4((TKey4) value);
                break;
            case 4:
                this.setKey5((TKey5) value);
                break;
            case 5:
                this.setKey6((TKey6) value);
                break;
            case 6:
                this.setKey7((TKey7) value);
                break;
        }
    }
}