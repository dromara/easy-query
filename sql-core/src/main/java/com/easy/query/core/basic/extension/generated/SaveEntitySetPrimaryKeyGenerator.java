package com.easy.query.core.basic.extension.generated;

import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2025/10/18 21:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SaveEntitySetPrimaryKeyGenerator {
    /**
     * 判断传入的对象是否设置过主键并且该主键是否由前端传入且保证正确
     * 如果不正确则可以通过{@link PrimaryKeyGenerator}或者{@link SaveEntitySetPrimaryKeyGenerator}进行设置
     *
     * <blockquote><pre>
     *     {@code
     *        //如果赋值的id确实是上下文追踪的那么不会进行处理
     *        //否则会用新的id进行复制
     *        //为什么要这么做因为eq认为id是应该有后端来生成而不是前端传入,前端传入的id只能是数据库已存在的id
     *        easyEntityQuery.saveEntitySetPrimaryKey(entity);
     *      }
     * </pre></blockquote>
     * @param entity
     * @param columnMetadata
     */
    void setPrimaryKey(Object entity, ColumnMetadata columnMetadata);
}
