package com.easy.query.core.proxy.core.draft;

import com.easy.query.core.basic.jdbc.executor.internal.enumerable.Draft;

/**
 * create time 2023/12/19 15:10
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractDraft implements Draft {
//    @Override
//    public void setPropTypes(int index, Class<?> propType) {
//
//    }
//
//    @Override
//    public Class<?> getPropType(int index) {
//        return null;
//    }
//
    @Override
    public boolean readColumn(int index) {
        return capacity() > index;
    }
}
