package com.easy.query.core.proxy.core.draft.group;

import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.core.draft.group.proxy.GroupKey1Proxy;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/23 13:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupKey1<TKey1> extends AbstractGroupKey implements ProxyEntityAvailable<GroupKey1<TKey1>, GroupKey1Proxy<TKey1>> {
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

    @Override
    public Class<GroupKey1Proxy<TKey1>> proxyTableClass() {
        return EasyObjectUtil.typeCastNullable(GroupKey1Proxy.class);
    }
}
