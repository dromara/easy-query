package com.easy.query.test.keytest;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import lombok.Data;

/**
 * create time 2024/6/20 22:57
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_test_p")
public class MyTestPrimaryKey {
    @Column(primaryKey = true,primaryKeyGenerator = MyTestPrimaryKeyGenerator.class)
    private String id;

}
