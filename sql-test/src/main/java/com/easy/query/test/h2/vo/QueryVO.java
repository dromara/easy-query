package com.easy.query.test.h2.vo;

import com.easy.query.core.annotation.EntityFileProxy;
import lombok.Data;

/**
 * create time 2023/12/17 14:25
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityFileProxy
public class  QueryVO {
    private String id;
    private String field1;
    private String field2;
}
