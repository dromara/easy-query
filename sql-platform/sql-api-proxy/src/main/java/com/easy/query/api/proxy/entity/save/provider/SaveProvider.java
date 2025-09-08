package com.easy.query.api.proxy.entity.save.provider;

import com.easy.query.api.proxy.entity.save.command.SaveCommand;

/**
 * create time 2025/9/6 21:05
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SaveProvider {
    SaveCommand createCommand();
}
