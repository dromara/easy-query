package com.easy.query.core.expression.sql.fill;

import com.easy.query.core.util.EasyStringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;


/**
 * create time 2025/10/29 08:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class FillContextImpl implements FillContext, FillRelation {
    private String selfProp;
    private String targetProp;
    private boolean consumeNull = false;

    @Override
    public String getSelfProp() {
        return selfProp;
    }

    @Override
    public String getTargetProp() {
        return targetProp;
    }

    public void validate() {
        if (EasyStringUtil.isBlank(selfProp)) {
            throw new IllegalArgumentException("selfProp is empty");
        }
        if (EasyStringUtil.isBlank(targetProp)) {
            throw new IllegalArgumentException("targetProp is empty");
        }
    }

    @Override
    public FillContext self_target(String selfProp, String targetProp, boolean consumeNull) {
        this.selfProp = selfProp;
        this.targetProp = targetProp;
        this.consumeNull = consumeNull;
        return this;
    }

    @Override
    public boolean isConsumeNull() {
        return consumeNull;
    }
}
