package com.easy.query.api.proxy.entity.save;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * create time 2025/9/5 20:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class SaveCommandContext {
    private final List<SavableContext> savableContexts;
    private final Class<?> entityClass;

    public SaveCommandContext(Class<?> entityClass) {
        this.savableContexts = new ArrayList<>();
        this.entityClass = entityClass;
    }

    public boolean circulateCheck(Class<?> targetEntityClass, int deep) {
        return Objects.equals(entityClass, targetEntityClass) && deep > 0;
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
