package com.easy.query.core.expression.sql.fill;

/**
 * create time 2025/10/29 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface FillRelation {
    String getSelfProp();
    String getTargetProp();
    boolean isConsumeNull();
}
