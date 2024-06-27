package com.easyquery.springbootdemo;

import com.easy.query.core.basic.extension.generated.PrimaryKeyGenerator;

import java.io.Serializable;

/**
 * create time 2024/6/27 13:47
 * 文件说明
 *
 * @author xuejiaming
 */
public class UUIDPrimaryKeyGenerator implements PrimaryKeyGenerator {
    @Override
    public Serializable getPrimaryKey() {
        return null;
    }
}
