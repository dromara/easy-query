package com.easy.query.core.metadata;

/**
 * create time 2025/8/30 20:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class TreeSelfTargetItem {
    public final String[] selfProperties;
    public final String[] targetProperties;

    public TreeSelfTargetItem(String[] selfProperties, String[] targetProperties){
        this.selfProperties = selfProperties;
        this.targetProperties = targetProperties;
    }
}
