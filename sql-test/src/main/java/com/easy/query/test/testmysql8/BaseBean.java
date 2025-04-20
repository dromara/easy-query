package com.easy.query.test.testmysql8;

import com.easy.query.core.annotation.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseBean {
    @Column(primaryKey = true, generatedKey = true)//设置主键为自增
    private int id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
