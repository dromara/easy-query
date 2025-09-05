package com.easy.query.api.proxy.entity.save;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2025/9/5 20:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class SaveCommandContext {
    private final List<SavableContext> savableContexts;

    public SaveCommandContext() {
        this.savableContexts = new ArrayList<>();
    }

    public List<SavableContext> getSavableContexts() {
        return savableContexts;
    }

    public SavableContext getSavableContext(int index) {
        if (index == savableContexts.size()) {
            savableContexts.add(new SavableContext(index));
        } else {
            if (index > savableContexts.size()) {
                throw new EasyQueryInvalidOperationException("index out of range");
            }
        }
        return savableContexts.get(index);
    }
}
