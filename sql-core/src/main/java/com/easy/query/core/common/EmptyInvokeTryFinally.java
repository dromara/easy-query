package com.easy.query.core.common;

import com.easy.query.core.basic.extension.track.InvokeTryFinally;

/**
 * create time 2023/8/27 11:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class EmptyInvokeTryFinally implements InvokeTryFinally {
    public static final InvokeTryFinally EMPTY =new EmptyInvokeTryFinally();
    @Override
    public void begin() {

    }

    @Override
    public void release() {

    }
}
