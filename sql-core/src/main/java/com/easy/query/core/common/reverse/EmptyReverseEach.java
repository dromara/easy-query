package com.easy.query.core.common.reverse;

import com.easy.query.core.basic.extension.track.InvokeTryFinally;
import com.easy.query.core.common.EmptyInvokeTryFinally;

/**
 * create time 2024/12/7 10:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class EmptyReverseEach implements ReverseEach{
    public static final ReverseEach EMPTY =new EmptyReverseEach();
    @Override
    public void invoke() {

    }
}
