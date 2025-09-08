package com.easy.query.test.dto;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

/**
 * create time 2025/9/3 10:11
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class UserBankDTO2 {
    private String name;
    private String phone;
    private String code2;
}
