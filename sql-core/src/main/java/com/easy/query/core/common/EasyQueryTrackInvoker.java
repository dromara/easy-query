package com.easy.query.core.common;

import com.easy.query.core.basic.extension.track.InvokeTryFinally;

/**
 * create time 2023/8/27 11:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryTrackInvoker implements InvokeTryFinally {

    private final InvokeTryFinally preview;
    private final InvokeTryFinally next;

    public EasyQueryTrackInvoker(InvokeTryFinally preview,InvokeTryFinally next){

        this.preview = preview;
        this.next = next;
    }
    @Override
    public void begin(){
        preview.begin();
        next.begin();
    }
    @Override
    public void release(){
        next.release();
        preview.release();
    }
}
