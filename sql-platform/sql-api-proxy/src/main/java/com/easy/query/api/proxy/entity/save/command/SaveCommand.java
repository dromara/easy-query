package com.easy.query.api.proxy.entity.save.command;

/**
 * create time 2025/9/6 21:06
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SaveCommand {
    default void execute(){
        execute(false);
    }
    void execute(boolean batch);
}
