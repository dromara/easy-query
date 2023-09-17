package com.easy.query.test.entity.base;

import com.easy.query.core.annotation.Column;
import lombok.Data;

/**
 * create time 2023/9/17 12:40
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public abstract class BaseGenericEntity<TKey> {
    @Column(primaryKey = true)
    private TKey id;
}
