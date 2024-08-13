package com.easy.query.test.entity;

import com.easy.query.core.annotation.Table;
import lombok.Data;

/**
 * create time 2024/8/13 15:08
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("aaa")
public class TestBeanProperty {

    private String cName;
    private String mySQL;
    private String mySql;
    private String SQL;
}
