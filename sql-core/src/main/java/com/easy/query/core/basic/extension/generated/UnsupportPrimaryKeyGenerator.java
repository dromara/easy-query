package com.easy.query.core.basic.extension.generated;

import java.io.Serializable;

/**
 * create time 2024/6/20 22:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class UnsupportPrimaryKeyGenerator implements PrimaryKeyGenerator {
//    public static final PrimaryKeyGenerator INSTANCE = new UnsupportPrimaryKeyGenerator();

    @Override
    public Serializable getPrimaryKey() {
        throw new UnsupportedOperationException();
    }
}
