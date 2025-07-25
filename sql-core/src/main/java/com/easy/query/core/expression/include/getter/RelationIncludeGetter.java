package com.easy.query.core.expression.include.getter;

import com.easy.query.core.expression.sql.include.RelationValue;

/**
 * create time 2025/3/29 21:24
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RelationIncludeGetter {
    /**
     * 是否包含结果如果不包含则不会执行{@link #getIncludeValue(RelationValue)}
     * @return
     */
    boolean include();

    /**
     * 根据关系键获取include目标值
     * @param relationValue 简单理解为self属性值
     * @return 需要返回self关联了哪些
     */
    Object getIncludeValue(RelationValue relationValue);
    Object getFlatPaddingValue();
}
