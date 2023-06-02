package com.easy.query.api4j.insert;

import com.easy.query.core.basic.api.insert.ClientInsertable;

/**
 * create time 2023/6/2 16:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEntityInsertable<T> extends AbstractEntityInsertable<T> {
    public EasyEntityInsertable(ClientInsertable<T> clientInsertable) {
        super(clientInsertable);
    }
}
