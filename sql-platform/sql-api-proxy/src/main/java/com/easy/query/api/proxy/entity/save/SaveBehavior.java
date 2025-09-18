package com.easy.query.api.proxy.entity.save;

import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.util.EasyBitwiseUtil;

/**
 * create time 2023/3/30 08:16
 * 文件说明
 *
 * @author xuejiaming
 */
public final class SaveBehavior {
    public static SaveBehavior DEFAULT=new SaveBehavior();
    public static int DEFAULT_BEHAVIOR = 0;
    private int behavior = DEFAULT_BEHAVIOR;

    public boolean isDefaultBehavior() {
        return behavior == 0;
    }

    public boolean hasBehavior(SaveBehaviorEnum easyBehavior) {
        return EasyBitwiseUtil.hasBit(behavior, easyBehavior.getCode());
    }

    public boolean addBehavior(SaveBehaviorEnum easyBehavior) {
        if (hasBehavior(easyBehavior)) {
            return false;
        } else {
            behavior = EasyBitwiseUtil.addBit(behavior, easyBehavior.getCode());
            return true;
        }
    }
    public SaveBehavior add(SaveBehaviorEnum easyBehavior){
        if (hasBehavior(easyBehavior)) {
            return this;
        } else {
            behavior = EasyBitwiseUtil.addBit(behavior, easyBehavior.getCode());
            return this;
        }
    }

    public boolean removeBehavior(SaveBehaviorEnum easyBehavior) {
        if (hasBehavior(easyBehavior)) {
            behavior = EasyBitwiseUtil.removeBit(behavior, easyBehavior.getCode());
            return true;
        }
        return false;
    }
    public SaveBehavior remove(SaveBehaviorEnum easyBehavior) {
        if (hasBehavior(easyBehavior)) {
            behavior = EasyBitwiseUtil.removeBit(behavior, easyBehavior.getCode());
            return this;
        }
        return this;
    }

    public void copyTo(SaveBehavior easyBehavior) {
        easyBehavior.behavior = this.behavior;
    }
}
