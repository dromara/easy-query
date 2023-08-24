package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import com.easy.query.test.increment.MyDatabaseIncrementSQLColumnGenerator;
import lombok.Data;

/**
 * create time 2023/8/9 10:53
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("custom_increment")
public class CustomIncrement {
    @Column(primaryKey = true,increment = true, generatedSQLColumnGenerator = MyDatabaseIncrementSQLColumnGenerator.class)
    private String id;
    private String name;
    private String address;
}
