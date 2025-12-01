package com.easy.query.api.proxy.entity.save;

import com.easy.query.api.proxy.entity.save.diff.PropertyDiffNode;
import com.easy.query.api.proxy.entity.save.diff.PropertyKey;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * create time 2025/9/5 20:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class SaveCommandContext2 {
    private final List<SavableContext> savableContexts;

    private final Class<?> entityClass;

    private final Set<Object> processEntitySet;

    public SaveCommandContext2(Class<?> entityClass) {
        this.savableContexts = new ArrayList<>();
        this.processEntitySet = new HashSet<>();
        this.entityClass = entityClass;
    }

    public boolean circulateCheck(Class<?> targetEntityClass, int deep) {
        return Objects.equals(entityClass, targetEntityClass) && deep > 0;
    }

    public void addProcessEntity(Object entity) {
        processEntitySet.add(entity);
    }

    public boolean isProcessEntity(Object entity) {
        return processEntitySet.contains(entity);
    }

    public List<SavableContext> getSavableContexts() {
        return savableContexts;
    }

    public SavableContext getCreateSavableContext(int index) {
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
