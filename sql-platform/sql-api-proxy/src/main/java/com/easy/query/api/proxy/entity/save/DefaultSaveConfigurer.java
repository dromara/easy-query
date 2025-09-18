package com.easy.query.api.proxy.entity.save;

/**
 * create time 2025/9/18 20:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSaveConfigurer implements SaveConfigurer{
    private final SaveBehavior saveBehavior;

    public DefaultSaveConfigurer(SaveBehavior saveBehavior){
        this.saveBehavior = saveBehavior;
    }
    @Override
    public SaveBehavior getSaveBehavior() {
        return saveBehavior;
    }
}
