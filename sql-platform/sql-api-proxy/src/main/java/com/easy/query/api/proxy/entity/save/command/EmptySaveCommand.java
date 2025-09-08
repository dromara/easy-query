package com.easy.query.api.proxy.entity.save.command;

/**
 * create time 2025/9/6 21:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class EmptySaveCommand implements SaveCommand{
    public static final SaveCommand INSTANCE=new EmptySaveCommand();
    @Override
    public void execute(boolean batch) {

    }
}
