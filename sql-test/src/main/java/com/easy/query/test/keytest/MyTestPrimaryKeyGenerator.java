package com.easy.query.test.keytest;

import com.easy.query.core.basic.extension.generated.PrimaryKeyGenerator;
import com.easy.query.core.metadata.ColumnMetadata;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * create time 2024/6/20 22:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyTestPrimaryKeyGenerator implements PrimaryKeyGenerator {
    @Override
    public Serializable getPrimaryKey() {
        return UUID.randomUUID().toString();
    }

}
