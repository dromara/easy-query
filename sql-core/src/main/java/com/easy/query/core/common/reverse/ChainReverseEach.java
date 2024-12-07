package com.easy.query.core.common.reverse;

/**
 * create time 2024/12/7 10:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class ChainReverseEach implements ReverseEach{
    private final ReverseEach preview;
    private final ReverseEach next;

    public ChainReverseEach(ReverseEach preview, ReverseEach next){
        this.preview = preview;
        this.next = next;
    }
    @Override
    public void invoke() {
        next.invoke();
        preview.invoke();
    }
}
